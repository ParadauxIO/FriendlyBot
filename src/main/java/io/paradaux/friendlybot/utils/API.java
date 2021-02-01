/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.API :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.utils;

import io.paradaux.friendlybot.managers.*;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
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

    public Logger getLogger() {
        return logger;
    }

    public IOManager getIoManager() {
        return ioManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ConfigurationEntry getConfigurationEntry() {
        return configurationEntry;
    }

    public MongoManager getMongoManager() {
        return mongoManager;
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    public DiscordBotManager getDiscordBotManager() {
        return discordBotManager;
    }

    public AuditManager getAuditManager() {
        return auditManager;
    }

    public MailGunManager getMailGunManager() {
        return mailGunManager;
    }

    public VerificationManager getVerificationManager() {
        return verificationManager;
    }
}
