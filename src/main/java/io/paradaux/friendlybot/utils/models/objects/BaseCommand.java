/*
 * Copyright (c) 2021 |  Rían Errity. GPLv3
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.friendlybot.utils.models.objects;

import com.jagrosh.jdautilities.command.Command;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.utils.embeds.notices.SyntaxErrorEmbed;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.exceptions.NoSuchUserException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public abstract class BaseCommand extends Command {

    private final ConfigurationEntry config;
    private final Logger logger;
    private final PermissionManager permissionManager;

    /**
     * Create a BaseCommand provided the configuration, logger and permission manager.
     * */
    public BaseCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager) {
        this.config = config;
        this.logger = logger;
        this.permissionManager = permissionManager;
    }

    /**
     * Create a BaseCommand provided the configuration and the logger.
     * */
    public BaseCommand(ConfigurationEntry config, Logger logger) {
        this.config = config;
        this.logger = logger;
        this.permissionManager = null;
    }

    /**
     * Create a BaseCommand provided the logger.
     * */
    public BaseCommand(Logger logger) {
        this.config = null;
        this.logger = logger;
        this.permissionManager = null;
    }

    /**
     * Create a BaseCommand provided the logger, and the permission manager.
     * */
    public BaseCommand(Logger logger, PermissionManager permissionManager) {
        this.config = null;
        this.logger = logger;
        this.permissionManager = permissionManager;
    }

    /**
     * Parses a sentence from the specified arguments, beginning at the specified array index.
     * */
    public String parseSentance(int startElement, String[] args) {
        return String.join(" ", Arrays.copyOfRange(args, startElement, args.length));
    }

    /**
     * Responds with a canned syntax error embed.
     * */
    public void respondSyntaxError(Message message, String correctSyntax) {
        message.addReaction("\uD83D\uDEAB").queue();
        new SyntaxErrorEmbed(message.getAuthor(), this.name, correctSyntax)
                .sendEmbed(message.getTextChannel());
    }

    /**
     * Used for commands which take a user as a parameter. Tries three ways of parsing the user. The first being by an @mention, the second
     * by a tag (Username#Discriminator) then finally via their discord id. Returns the user object if found, otherwise null.
     * */
    @CheckReturnValue
    @Nullable
    public User parseTarget(Message message, String userInput) throws NoSuchUserException {
        try {
            if (message.getMentionedMembers().size() == 1) {
                return message.getMentionedMembers().get(0).getUser();
            }

            User user;
            try {
                user = message.getGuild().getJDA().getUserByTag(userInput);
            } catch (IllegalArgumentException ex) {
                user = message.getJDA().retrieveUserById(userInput).submit().get();
            }
            
            return user;
        } catch (InterruptedException | ExecutionException | NumberFormatException exception) {
            throw new NoSuchUserException(exception.getMessage());
        }
    }

    /**
     * Gets a message by guild and ID.
     * */
    @CheckReturnValue
    @Nullable
    public Member retrieveMember(Guild guild, User user) {
        try {
            return guild.retrieveMember(user)
                    .submit()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Interrupted Exception", e);
        }

        return null;
    }

    /**
     * Parse arguments by splitting by space.
     * */
    @CheckReturnValue
    @Nonnull
    public String[] getArgs(String args) {
        return args.split(" ");
    }

    @CheckReturnValue
    @Nullable
    public ConfigurationEntry getConfig() {
        return config;
    }

    @CheckReturnValue
    @Nonnull
    public Logger getLogger() {
        return logger;
    }

    @CheckReturnValue
    @Nullable
    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    /**
     * Deletes the message associated with the specified message object.
     * */
    public void delete(Message message) {
        message.delete().queue();
    }
}
