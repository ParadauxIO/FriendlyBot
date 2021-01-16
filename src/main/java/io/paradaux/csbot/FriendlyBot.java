

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

package io.paradaux.csbot;

import io.paradaux.csbot.managers.*;
import io.paradaux.csbot.models.interal.ConfigurationEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CSBot is the main (executable) class for the project.
 * It provides a set of lazy dependency injection instances
 *
 * @author Rían Errity
 * @version Last Modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 */

public class FriendlyBot {

    /**
     * This is the main method for the application. It handles the instantiation
     * of dependencies, and the discord bot as a whole
     *
     * @param args Command line arguments are not used in this application.
     */
    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger("io.paradaux.csbot");
        logger.info("CSBot v0.1.0 - Maintained by Rían Errity <rian@paradaux.io>");

        System.out.println("Initialising Controllers. This may take some time...");

        IOManager ioManager = new IOManager(logger);
        ConfigManager configManager = new ConfigManager(logger);

        ConfigurationEntry config = configManager.getConfig();

        // Configuration-dependant managers.
        SMTPManager smtpManager = new SMTPManager(config, logger);
        MongoManager mongoManager = new MongoManager(config, logger);
        PermissionManager permissionManager = new PermissionManager(logger);
        DiscordBotManager discordBotManager = new DiscordBotManager(config, logger, permissionManager);
        AuditManager auditManager = new AuditManager(config, logger);
        VerificationManager verificationManager = new VerificationManager(config, logger, mongoManager);

        // TODO Add an API which exposes the above controllers.
    }

}
