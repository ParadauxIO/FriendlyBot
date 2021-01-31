/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.utils.models.configuration;

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

    // Mailgun: Email Verification
    String mailgunApi;
    String mailgunUrl;

    // Wolfram Alpha
    String wolframApiKey;
    String imgurClientId;

    // Constructor for builder-pattern generation
    public ConfigurationEntry() {

    }

    // General constructor.

    public ConfigurationEntry(String botToken, String commandPrefix, String csFriendlyGuildID, String auditLogChannelID,
                              String verificationChannelID, String verifiedRoleID, String modmailInputChannelID,
                              String modmailOutputChannelID, String mongoConnectionUri, String mailgunApi, String mailgunUrl,
                              String wolframApiKey, String imgurClientId) {
        this.botToken = botToken;
        this.commandPrefix = commandPrefix;
        this.csFriendlyGuildID = csFriendlyGuildID;
        this.auditLogChannelID = auditLogChannelID;
        this.verificationChannelID = verificationChannelID;
        this.verifiedRoleID = verifiedRoleID;
        this.modmailInputChannelID = modmailInputChannelID;
        this.modmailOutputChannelID = modmailOutputChannelID;
        this.mongoConnectionUri = mongoConnectionUri;
        this.mailgunApi = mailgunApi;
        this.mailgunUrl = mailgunUrl;
        this.wolframApiKey = wolframApiKey;
        this.imgurClientId = imgurClientId;
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

    public String getMailgunApi() {
        return mailgunApi;
    }

    public String getMailgunUrl() {
        return mailgunUrl;
    }

    public String getWolframApiKey() {
        return wolframApiKey;
    }

    public String getImgurClientId() {
        return imgurClientId;
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

    public ConfigurationEntry setMailgunApi(String mailgunApi) {
        this.mailgunApi = mailgunApi;
        return this;
    }

    public ConfigurationEntry setMailgunUrl(String mailgunUrl) {
        this.mailgunUrl = mailgunUrl;
        return this;
    }

    public ConfigurationEntry setWolframApiKey(String wolframApiKey) {
        this.wolframApiKey = wolframApiKey;
        return this;
    }

    public ConfigurationEntry setImgurClientId(String imgurClientId) {
        this.imgurClientId = imgurClientId;
        return this;
    }
}
