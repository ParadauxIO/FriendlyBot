

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

import io.paradaux.csbot.controllers.*;
import org.slf4j.Logger;

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

        // Instantiate Logger instance, so we can log the other controllers properly.
        System.out.println("Initialising Controllers. This may take some time...");
        new LogController().initialise();
        Logger logger = LogController.getLogger();

        // Prepare File Controller, which includes various utilities used by the below controllers.
        logger.info("Initialising: FileController");
        new FileController().initialise();

        // Prepare Configuration File/Cache
        logger.info("Initialising: ConfigurationController");
        new ConfigurationController().initialise();

        // Email
        logger.info("Initialising: EmailController");
        new EmailController().initialise();

        // Database
        logger.info("Initialising: Database Controller");
        new DatabaseController().initialise();

        // Reaction Role
        logger.info("Initialising: ReactionRoleController");
        new ReactionRoleController().initialise();

        // Permissions
        logger.info("Initialising: PermissionController");
        new PermissionController().initialise();

        // Commands
        logger.info("Initialising: CommandController");
        new CommandController().initialise();

        // ModerationAction
        logger.info("Initialising: ModerationActionController");

        // The Bot
        logger.info("Initialising: BotController");
        new BotController().initialise();

        // Audit Log
        logger.info("Initialising: AuditLogController");
        new AuditLogController().initialise();
    }

}
