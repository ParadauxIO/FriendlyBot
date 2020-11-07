/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.

 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.

 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).

 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.csbot.api;

import io.paradaux.csbot.controllers.ConfigurationController;

import java.util.List;

/**
 * Contains a cached copy of the configuration file's contents, to allow easy access within the application
 *
 * @author Rían Errity
 * @version Last modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 * @see ConfigurationController
 * */

public class ConfigurationCache {

    String token;
    String prefix;
    String guild;
    String listeningChannel;
    List<String> admins;
    String verifiedRole;
    String mongoUri;
    String smtpUser;
    String smtpPass;
    String smtpServer;
    String smtpPort;

    public ConfigurationCache(String token, String prefix, String guild, String listeningChannel, List<String> admins, String verifiedRole, String mongoUri, String smtpUser, String smtpPass, String smtpServer, String smtpPort) {
        this.token = token;
        this.prefix = prefix;
        this.guild = guild;
        this.listeningChannel = listeningChannel;
        this.admins = admins;
        this.verifiedRole = verifiedRole;
        this.mongoUri = mongoUri;
        this.smtpUser = smtpUser;
        this.smtpPass = smtpPass;
        this.smtpServer = smtpServer;
        this.smtpPort = smtpPort;
    }

    /**
     * @return Discord bot token.
     * */
    public String getToken() {
        return token;
    }

    /**
     * @return Command Prefix
     * */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @return List of Discord IDs which represent the administrators.
     * */
    public List<String> getAdmins() {
        return admins;
    }

    /**
     * @return The Role ID which represents the Verified Role.
     * */
    public String getVerifiedRole() {
        return verifiedRole;
    }

    /**
     * @return The Channel ID which the bot will listen to email addresses to.
     */
    public String getListeningChannel() {
        return listeningChannel;
    }

    /**
     * @return The MongoURI used to connect to the MongoDB Server
     * */
    public String getMongoUri() {
        return mongoUri;
    }

    /**
     * @return The SMTP Username
     * */
    public String getSmtpUser() {
        return smtpUser;
    }

    /**
     * @return The SMTP Password
     * */
    public String getSmtpPass() {
        return smtpPass;
    }

    /**
     * @return The SMTP FQDN.
     * */
    public String getSmtpServer() {
        return smtpServer;
    }

    /**
     * @return The SMTP Server Port.
     * */
    public String getSmtpPort() {
        return smtpPort;
    }

    /**
     * @return The ID of the Computer Science Friendly Discord
     * */
    public String getGuild() {
        return guild;
    }
}
