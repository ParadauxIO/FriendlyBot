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

package io.paradaux.csbot.controllers;

import io.paradaux.csbot.models.interal.ConfigurationEntry;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.slf4j.Logger;

import java.util.concurrent.ExecutionException;

public class VerificationSystemController {

    public static VerificationSystemController INSTANCE;
    private static final Logger logger = LogController.getLogger();
    private static DatabaseController databaseController;
    private static ConfigurationEntry configurationEntry;


    public VerificationSystemController() {
        logger.info("Initialising: PermissionController");
        databaseController = DatabaseController.INSTANCE;
        configurationEntry = ConfigurationController.getConfigurationEntry();
        INSTANCE = this;
    }

    public boolean setVerified(String discordID, String guildID, String verificationCode) {
        if (!verificationCode.equals(databaseController.getVerificationCode(discordID))) {
            return false;
        }

        databaseController.setVerifiedUser(discordID, guildID);

        Guild guild = BotController.getClient().getGuildById(guildID);

        if (guild == null) {
            return false;
        }

        Role role = guild.getRoleById(configurationEntry.getVerifiedRoleID());

        if (role == null) {
            return false;
        }

        try {
            Member member = guild.retrieveMemberById(discordID).submit().get();
            guild.addRoleToMember(member, role).queue();
            return true;
        } catch (InterruptedException | ExecutionException exception) {
            return false;
        }
    }


}
