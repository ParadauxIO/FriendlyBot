/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.FriendlyBot :  31/01/2021, 01:26
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.paradaux.friendlybot;

import io.paradaux.friendlybot.managers.*;
import io.paradaux.friendlybot.utils.API;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * CSBot is the main (executable) class for the project.
 * It provides a set of lazy dependency injection instances
 *
 * @author Rían Errity
 * @version Last Modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 */

public class FriendlyBot {

    private static API api;
    public static API getApi() {
        return api;
    }

    /**
     * This is the main method for the application. It handles the instantiation
     * of dependencies, and the discord bot as a whole
     *
     * @param args Command line arguments are not used in this application.
     */
    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger("io.paradaux.friendlybot");
        logger.info("CSBot v1.0.0 - Maintained by Rían Errity <rian@paradaux.io>");
        logger.info("Initialising Controllers. This may take some time...");

        IOManager ioManager = new IOManager(logger);

        ConfigManager configManager = new ConfigManager(logger);
        ConfigurationEntry config = configManager.getConfig();

        MongoManager mongoManager = new MongoManager(config, logger);
        PermissionManager permissionManager = new PermissionManager(logger);
        RoleManager roleManager = new RoleManager(logger, mongoManager);
        UserSettingsManager settingsManager = new UserSettingsManager(logger, mongoManager);
        GuildSettingsManager guildSettingsManager = new GuildSettingsManager(logger, mongoManager);
        PunishmentManager punishmentManager = new PunishmentManager();
        DiscordBotManager discordBotManager = new DiscordBotManager(config, logger, permissionManager, mongoManager, roleManager, guildSettingsManager);
        TagManager tagManager = new TagManager(config, logger, mongoManager);
        AuditManager auditManager = new AuditManager(config, logger);
        MailGunManager mailGunManager = new MailGunManager(config, logger);
        VerificationManager verificationManager = new VerificationManager(config, logger, mongoManager, mailGunManager);
        
        api = API.builder()
                .setLogger(logger)
                .setIoManager(ioManager)
                .setConfigManager(configManager)
                .setConfigurationEntry(config)
                .setMongoManager(mongoManager)
                .setPermissionManager(permissionManager)
                .setDiscordBotManager(discordBotManager)
                .setAuditManager(auditManager)
                .setVerificationManager(verificationManager)
                .build();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down...");
        }));

        // TODO unban scheduler
        // TODO update API
        // TODO Sentry integration

    }
}
