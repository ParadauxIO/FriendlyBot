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

package io.paradaux.friendlybot.managers;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import io.paradaux.friendlybot.commands.fun.InspireCommand;
import io.paradaux.friendlybot.commands.fun.PingCommand;
import io.paradaux.friendlybot.commands.fun.XKCDCommand;
import io.paradaux.friendlybot.commands.staff.moderation.*;
import io.paradaux.friendlybot.commands.staff.technician.*;
import io.paradaux.friendlybot.commands.utility.*;
import io.paradaux.friendlybot.listeners.ReadyListener;
import io.paradaux.friendlybot.listeners.modmail.ModMailChannelListener;
import io.paradaux.friendlybot.listeners.modmail.ModMailPrivateMessageListener;
import io.paradaux.friendlybot.listeners.verification.VerificationCodeReceivedListener;
import io.paradaux.friendlybot.listeners.verification.VerificationEmailReceivedListener;
import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;

public class DiscordBotManager {

    // Singleton Instance
    private static DiscordBotManager instance = null;
    private final ConfigurationEntry config;
    private final Logger logger;
    private final PermissionManager permissionManager;
    private final MongoManager mongo;
    private final JDA client;

    public DiscordBotManager(ConfigurationEntry config, Logger logger, PermissionManager permissionManager, MongoManager mongo) {
        this.config = config;
        this.logger = logger;
        this.permissionManager = permissionManager;
        this.mongo = mongo;

        logger.info("Initialising: BotController");
        logger.info("Attempting to login");

        try {
            client = login(config.getBotToken());
        } catch (LoginException e) {
            throw new RuntimeException("Failed to login");
        }

        logger.info("Login successful.");

        instance = this;
    }

    public static DiscordBotManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

    private CommandClient createCommandClient() {
        logger.info("Initialising: CommandController");
        CommandClientBuilder builder = new CommandClientBuilder()
                .setPrefix(config.getCommandPrefix())
                .setOwnerId("150993042558418944")
                .setActivity(Activity.playing("with with DCEVM"))
                .addCommands(
                        // Fun Commands
                        new InspireCommand(config, logger),
                        new PingCommand(logger),
                        new XKCDCommand(config, logger),

                        // Moderation Commands
                        new BanCommand(config, logger, permissionManager),
                        new CiteCommand(config, logger, permissionManager),
                        new KickCommand(config, logger, permissionManager),
                        new LookupCommand(config, logger, permissionManager),
                        new PruneCommand(config, logger, permissionManager),
                        new RespondCommand(config, logger, permissionManager, mongo),
                        new TicketCommand(config, logger, permissionManager),
                        new TimeOutCommand(config, logger, permissionManager),
                        new WarnCommand(config, logger, permissionManager),

                        // Technician Commands.
                        new DmCommand(config, logger, permissionManager),
                        new PermissionsCommand(config, logger, permissionManager),
                        new SayCommand(config, logger, permissionManager),
                        new SendEmailCommand(config, logger, permissionManager),
                        new SendEmbedCommand(config, logger, permissionManager),
                        new TagSetCommand(config, logger, permissionManager),
                        new VerificationCommand(config, logger, permissionManager),

                        // Utility Commands
                        new GithubCommand(config, logger),
                        new InviteCommand(logger),
                        new JavadocSearchCommand(config, logger),
                        new UserInfoCommand(config, logger, permissionManager),
                        new WolframAlphaCommand(config, logger)
                );

        return builder.build();
    }

    /**
     * Creates an Instance of JDA from the provided token.
     * @param token The Discord Token taken from the configuration file.
     * @return JDA An Instance of JDA
     * @throws LoginException When logging in proved unsuccessful.
     * */
    public JDA login(String token) throws LoginException {

        CommandClient commandClient = createCommandClient();

        JDABuilder builder = JDABuilder.createDefault(token)
                .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .setBulkDeleteSplittingEnabled(false)
                .addEventListeners(commandClient,
                        new ModMailChannelListener(config, logger),
                        new ModMailPrivateMessageListener(logger),
                        new VerificationCodeReceivedListener(config, logger),
                        new VerificationEmailReceivedListener(config, logger),
                        new ReadyListener(logger)
                );

        if (token == null) {
            throw new LoginException("The Configuration File does not contain a token.");
        }

        return builder.build();
    }

    @Nonnull
    @CheckReturnValue
    public JDA getClient() {
        return client;
    }

    @Nonnull
    @CheckReturnValue
    public Guild getGuild(String guildId) {
        Guild guild = client.getGuildById(guildId);

        if (guild == null) {
            throw new RuntimeException("Guild came back null.");
        }

        return guild;
    }

    @Nonnull
    @CheckReturnValue
    public TextChannel getChannel(String channelId) {
        TextChannel channel = client.getTextChannelById(channelId);

        if (channel == null) {
            throw new RuntimeException("Channel came back null.");
        }

        return channel;
    }

    @Nonnull
    @CheckReturnValue
    public Role getRole(String guildId, String roleId) {
        Guild guild = getGuild(guildId);
        Role role = guild.getRoleById(roleId);

        if (role == null) {
            throw new RuntimeException("Role came back null");
        }

        return role;

    }

    @Nonnull
    @CheckReturnValue
    public User getUser(String userId) {
        User user = client.getUserById(userId);

        if (user == null) {
            throw new RuntimeException("User came back null.");
        }

        return user;
    }

}
