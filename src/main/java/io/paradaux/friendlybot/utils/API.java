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

package io.paradaux.friendlybot.utils;

import io.paradaux.friendlybot.managers.*;
import io.paradaux.friendlybot.utils.models.interal.ConfigurationEntry;
import org.slf4j.Logger;

public class API {

    private Logger logger;
    private IOManager ioManager;
    private ConfigManager configManager;
    private ConfigurationEntry configurationEntry;
    private SMTPManager smtpManager;
    private MongoManager mongoManager;
    private PermissionManager permissionManager;
    private DiscordBotManager discordBotManager;
    private AuditManager auditManager;
    private VerificationManager verificationManager;

    private API() {
        // Use the builder!
    }

    public static Builder builder() {
        return new Builder();
    }




    public static class Builder {

        private final API api;

        private Builder() {
            // Use the factory method!
            api = new API();
        }

        public Builder setLogger(Logger logger) {
            api.logger = logger;
            return this;
        }

        public Builder setIoManager(IOManager ioManager) {
            api.ioManager = ioManager;
            return this;
        }

        public Builder setConfigManager(ConfigManager configManager) {
            api.configManager = configManager;
            return this;
        }

        public Builder setConfigurationEntry(ConfigurationEntry configurationEntry) {
            api.configurationEntry = configurationEntry;
            return this;
        }

        public Builder setSmtpManager(SMTPManager smtpManager) {
            api.smtpManager = smtpManager;
            return this;
        }

        public Builder setMongoManager(MongoManager mongoManager) {
            api.mongoManager = mongoManager;
            return this;
        }

        public Builder setPermissionManager(PermissionManager permissionManager) {
            api.permissionManager = permissionManager;
            return this;
        }

        public Builder setDiscordBotManager(DiscordBotManager discordBotManager) {
            api.discordBotManager = discordBotManager;
            return this;
        }

        public Builder setAuditManager(AuditManager auditManager) {
            api.auditManager = auditManager;
            return this;
        }

        public Builder setVerificationManager(VerificationManager verificationManager) {
            api.verificationManager = verificationManager;
            return this;
        }

        public API build() {
            return api;
        }
        
        

    }

}
