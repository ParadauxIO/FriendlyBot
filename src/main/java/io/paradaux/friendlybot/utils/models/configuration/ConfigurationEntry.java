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

package io.paradaux.friendlybot.utils.models.interal;

import io.paradaux.friendlybot.managers.ConfigManager;

/**
 * Contains a cached copy of the configuration file's contents, to allow easy access within the
 * application.
 *
 * @author Rían Errity
 * @version Last modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 * @see ConfigManager
 * */

public class ConfigurationEntry {

    // Required to have a discord bot
    String botToken;
    String commandPrefix;

    // Used to access instances of particular channels / to give roles
    String csFriendlyGuildID;

    // Audit Log System Configuration
    String auditLogChannelID;

    // Verification System Configuration
    String verificationChannelID;
    String verifiedRoleID;

    // Modmail System Configuration
    String modmailInputChannelID;
    String modmailOutputChannelID;

    // Database Controller Configuration
    String mongoConnectionUri;

    // SMTP Login Information.
    String smtpUser;
    String smtpPass;
    String smtpHost;
    String smtpPort;

    // Constructor for builder-pattern generation
    public ConfigurationEntry() {

    }

    // General constructor.
    public ConfigurationEntry(String botToken, String commandPrefix, String csFriendlyGuildID,
                              String auditLogChannelID, String verificationChannelID,
                              String verifiedRoleID, String modmailInputChannelID,
                              String modmailOutputChannelID, String mongoConnectionUri,
                              String smtpUser, String smtpPass, String smtpHost, String smtpPort) {
        this.botToken = botToken;
        this.commandPrefix = commandPrefix;
        this.csFriendlyGuildID = csFriendlyGuildID;
        this.auditLogChannelID = auditLogChannelID;
        this.verificationChannelID = verificationChannelID;
        this.verifiedRoleID = verifiedRoleID;
        this.modmailInputChannelID = modmailInputChannelID;
        this.modmailOutputChannelID = modmailOutputChannelID;
        this.mongoConnectionUri = mongoConnectionUri;
        this.smtpUser = smtpUser;
        this.smtpPass = smtpPass;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
    }

    /*
     * Standard Getters
     * */

    public String getBotToken() {
        return botToken;
    }

    public String getCommandPrefix() {
        return commandPrefix;
    }

    public String getCsFriendlyGuildID() {
        return csFriendlyGuildID;
    }

    public String getVerificationChannelID() {
        return verificationChannelID;
    }

    public String getVerifiedRoleID() {
        return verifiedRoleID;
    }

    public String getAuditLogChannelID() {
        return auditLogChannelID;
    }

    public String getModmailInputChannelID() {
        return modmailInputChannelID;
    }

    public String getModmailOutputChannelID() {
        return modmailOutputChannelID;
    }

    public String getMongoConnectionUri() {
        return mongoConnectionUri;
    }

    public String getSmtpUser() {
        return smtpUser;
    }

    public String getSmtpPass() {
        return smtpPass;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    /*
     * Builder-pattern Setters
     * Mostly for easy mocking/unit testing
     * */

    public ConfigurationEntry setBotToken(String botToken) {
        this.botToken = botToken;
        return this;
    }

    public ConfigurationEntry setCommandPrefix(String commandPrefix) {
        this.commandPrefix = commandPrefix;
        return this;
    }

    public ConfigurationEntry setCsFriendlyGuildID(String csFriendlyGuildID) {
        this.csFriendlyGuildID = csFriendlyGuildID;
        return this;
    }

    public ConfigurationEntry setVerificationChannelID(String verificationChannelID) {
        this.verificationChannelID = verificationChannelID;
        return this;
    }

    public ConfigurationEntry setAuditLogChannelID(String auditLogChannelID) {
        this.auditLogChannelID = auditLogChannelID;
        return this;
    }

    public ConfigurationEntry setVerifiedRoleID(String verifiedRoleID) {
        this.verifiedRoleID = verifiedRoleID;
        return this;
    }

    public ConfigurationEntry setModmailInputChannelID(String modmailInputChannelID) {
        this.modmailInputChannelID = modmailInputChannelID;
        return this;
    }

    public ConfigurationEntry setModmailOutputChannelID(String modmailOutputChannelID) {
        this.modmailOutputChannelID = modmailOutputChannelID;
        return this;
    }

    public ConfigurationEntry setMongoConnectionUri(String mongoConnectionUri) {
        this.mongoConnectionUri = mongoConnectionUri;
        return this;
    }

    public ConfigurationEntry setSmtpUser(String smtpUser) {
        this.smtpUser = smtpUser;
        return this;
    }

    public ConfigurationEntry setSmtpPass(String smtpPass) {
        this.smtpPass = smtpPass;
        return this;
    }

    public ConfigurationEntry setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
        return this;
    }

    public ConfigurationEntry setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
        return this;
    }
}
