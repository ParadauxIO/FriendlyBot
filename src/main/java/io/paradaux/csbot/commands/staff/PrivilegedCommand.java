/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
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

package io.paradaux.csbot.commands.staff;

import com.jagrosh.jdautilities.command.Command;
import io.paradaux.csbot.controllers.ConfigurationController;
import io.paradaux.csbot.controllers.LogController;
import io.paradaux.csbot.controllers.PermissionController;
import io.paradaux.csbot.embeds.command.NoPermissionEmbed;
import io.paradaux.csbot.embeds.command.SyntaxErrorEmbed;
import io.paradaux.csbot.models.interal.ConfigurationEntry;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public abstract class PrivilegedCommand extends Command {

    private static final ConfigurationEntry configurationEntry
            = ConfigurationController.getConfigurationEntry();
    private static final Logger logger = LogController.getLogger();
    private static final PermissionController permissionController = PermissionController.INSTANCE;

    public boolean isStaff(String discordID) {
        return permissionController.isAdmin(discordID)
                || permissionController.isMod(discordID)
                || permissionController.isTechnician(discordID);
    }

    public boolean isMod(String discordID) {
        return permissionController.isMod(discordID)
                || permissionController.isAdmin(discordID);
    }

    public boolean isNotManagement(String discordID) {
        return !permissionController.isAdmin(discordID)
                && !permissionController.isTechnician(discordID);
    }

    public String parseSentance(int startElement, String[] args) {
        return String.join(" ", Arrays.copyOfRange(args, startElement, args.length));
    }

    public void respondNoPermission(Message message, String requiredRole) {
        new NoPermissionEmbed(message.getAuthor(), this.name, requiredRole)
                .sendEmbed(message.getTextChannel());
    }

    public void respondSyntaxError(Message message, String correctSyntax) {
        new SyntaxErrorEmbed(message.getAuthor(), this.name, correctSyntax)
                .sendEmbed(message.getTextChannel());
    }

    @Nullable
    public User parseTarget(Message message, int targetPlacement, String[] args) {
        if (message.getMentionedMembers().size() == 1) {
            return message.getMentionedMembers().get(0).getUser();
        }

        try {
            return message.getGuild().retrieveMemberById(args[targetPlacement])
                    .submit()
                    .get()
                    .getUser();
        } catch (InterruptedException e) {
            logger.error("Interrupted Exception", e);
        } catch (ExecutionException e) {
            logger.error("Execution Exception", e);
        }

        return null;
    }

    @Nullable
    public Member retrieveMember(Guild guild, User user) {
        try {
            return guild.retrieveMember(user)
                    .submit()
                    .get();
        } catch (InterruptedException e) {
            logger.error("Interrupted Exception", e);
        } catch (ExecutionException e) {
            logger.error("Execution Exception", e);
        }
        return null;
    }

    public String[] getArgs(String args) {
        return args.split(" ");
    }

    public void delete(Message message) {
        message.delete().queue();
    }

    public static ConfigurationEntry getConfigurationEntry() {
        return configurationEntry;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static PermissionController getPermissionController() {
        return permissionController;
    }

}
