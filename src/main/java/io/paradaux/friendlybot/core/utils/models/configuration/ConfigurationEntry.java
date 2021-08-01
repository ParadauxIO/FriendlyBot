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

package io.paradaux.friendlybot.core.utils.models.configuration;

import com.google.gson.annotations.SerializedName;
import io.paradaux.friendlybot.managers.ConfigManager;

import java.io.Serializable;

/**
 * Contains a cached copy of the configuration file's contents, to allow easy access within the
 * application.
 *
 * @author Rían Errity
 * @version Last modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 * @see ConfigManager
 * */
public class ConfigurationEntry implements Serializable {

    protected static final long serialVersionUID = 2L;

    @SerializedName("bot_token")
    private String botToken;

    @SerializedName("command_prefix")
    private String commandPrefix;

    @SerializedName("guild_id")
    private String guildId;

    @SerializedName("verified_role_id")
    private String verifiedRoleId;

    @SerializedName("verification_input_channel_id")
    private String verificationInputChannelId;

    @SerializedName("private_auditlog_channel_id")
    private String privateAuditLogChannelId;

    @SerializedName("public_auditlog_channel_id")
    private String publicAuditLogChannelId;

    @SerializedName("modmail_input_channel_id")
    private String modMailInputChannel;

    @SerializedName("modmail_output_channel_id")
    private String modMailOutputChannel;

    @SerializedName("mongodb_connection_uri")
    private String mongoDbConnectionUri;

    @SerializedName("mailgun_application_key")
    private String mailGunApplicationKey;

    @SerializedName("verification_email_subject")
    private String verificationEmailSubject;

    @SerializedName("verification_email_sender")
    private String verificationEmailSender;

    @SerializedName("verification_email_template_name")
    private String verificationEmailTemplateName;

    @SerializedName("mailgun_base_url")
    private String mailGunBaseUrl;

    @SerializedName("wolfram_alpha_application_id")
    private String wolframAlphaApplicationId;

    @SerializedName("imgur_client_id")
    private String imgurClientId;

    @SerializedName("message_log_channel")
    private String messageLogChannel;

    @SerializedName("bytebin_instance")
    private String bytebinInstance;

    @SerializedName("imgflip_username")
    private String imgflipUsername;

    @SerializedName("imgflip_password")
    private String imgflipPassword;

    @SerializedName("openweathermap_api_key")
    private String weatherApiKey;

    @SerializedName("sentry_dsn")
    private String sentryDsn;

    public ConfigurationEntry() {
    
    }

    public ConfigurationEntry(String botToken, String commandPrefix, String guildId, String verifiedRoleId,
                              String verificationInputChannelId, String privateAuditLogChannelId, String publicAuditLogChannelId,
                              String modMailInputChannel, String modMailOutputChannel, String mongoDbConnectionUri,
                              String mailGunApplicationKey, String verificationEmailSubject, String verificationEmailSender,
                              String verificationEmailTemplateName, String mailGunBaseUrl, String wolframAlphaApplicationId,
                              String imgurClientId, String messageLogChannel, String bytebinInstance, String imgflipUsername,
                              String imgflipPassword, String weatherApiKey, String sentryDsn) {
        this.botToken = botToken;
        this.commandPrefix = commandPrefix;
        this.guildId = guildId;
        this.verifiedRoleId = verifiedRoleId;
        this.verificationInputChannelId = verificationInputChannelId;
        this.privateAuditLogChannelId = privateAuditLogChannelId;
        this.publicAuditLogChannelId = publicAuditLogChannelId;
        this.modMailInputChannel = modMailInputChannel;
        this.modMailOutputChannel = modMailOutputChannel;
        this.mongoDbConnectionUri = mongoDbConnectionUri;
        this.mailGunApplicationKey = mailGunApplicationKey;
        this.verificationEmailSubject = verificationEmailSubject;
        this.verificationEmailSender = verificationEmailSender;
        this.verificationEmailTemplateName = verificationEmailTemplateName;
        this.mailGunBaseUrl = mailGunBaseUrl;
        this.wolframAlphaApplicationId = wolframAlphaApplicationId;
        this.imgurClientId = imgurClientId;
        this.messageLogChannel = messageLogChannel;
        this.bytebinInstance = bytebinInstance;
        this.imgflipUsername = imgflipUsername;
        this.imgflipPassword = imgflipPassword;
        this.weatherApiKey = weatherApiKey;
        this.sentryDsn = sentryDsn;
    }

    public ConfigurationEntry setBotToken(String botToken) {
        this.botToken = botToken;
        return this;
    }

    public ConfigurationEntry setCommandPrefix(String commandPrefix) {
        this.commandPrefix = commandPrefix;
        return this;
    }

    public ConfigurationEntry setGuildId(String guildId) {
        this.guildId = guildId;
        return this;
    }

    public ConfigurationEntry setVerifiedRoleId(String verifiedRoleId) {
        this.verifiedRoleId = verifiedRoleId;
        return this;
    }

    public ConfigurationEntry setVerificationInputChannelId(String verificationInputChannelId) {
        this.verificationInputChannelId = verificationInputChannelId;
        return this;
    }

    public ConfigurationEntry setPrivateAuditLogChannelId(String privateAuditLogChannelId) {
        this.privateAuditLogChannelId = privateAuditLogChannelId;
        return this;
    }

    public ConfigurationEntry setPublicAuditLogChannelId(String publicAuditLogChannelId) {
        this.publicAuditLogChannelId = publicAuditLogChannelId;
        return this;
    }

    public ConfigurationEntry setModMailInputChannel(String modMailInputChannel) {
        this.modMailInputChannel = modMailInputChannel;
        return this;
    }

    public ConfigurationEntry setModMailOutputChannel(String modMailOutputChannel) {
        this.modMailOutputChannel = modMailOutputChannel;
        return this;
    }

    public ConfigurationEntry setMongoDbConnectionUri(String mongoDbConnectionUri) {
        this.mongoDbConnectionUri = mongoDbConnectionUri;
        return this;
    }

    public ConfigurationEntry setMailGunApplicationKey(String mailGunApplicationKey) {
        this.mailGunApplicationKey = mailGunApplicationKey;
        return this;
    }

    public ConfigurationEntry setMailGunBaseUrl(String mailGunBaseUrl) {
        this.mailGunBaseUrl = mailGunBaseUrl;
        return this;
    }

    public ConfigurationEntry setVerificationEmailSubject(String verificationEmailSubject) {
        this.verificationEmailSubject = verificationEmailSubject;
        return this;
    }

    public ConfigurationEntry setVerificationEmailSender(String verificationEmailSender) {
        this.verificationEmailSender = verificationEmailSender;
        return this;
    }

    public ConfigurationEntry setVerificationEmailTemplateName(String verificationEmailTemplateName) {
        this.verificationEmailTemplateName = verificationEmailTemplateName;
        return this;
    }

    public ConfigurationEntry setWolframAlphaApplicationId(String wolframAlphaApplicationId) {
        this.wolframAlphaApplicationId = wolframAlphaApplicationId;
        return this;
    }

    public ConfigurationEntry setImgurClientId(String imgurClientId) {
        this.imgurClientId = imgurClientId;
        return this;
    }

    public ConfigurationEntry setMessageLogChannel(String messageLogChannel) {
        this.messageLogChannel = messageLogChannel;
        return this;
    }

    public ConfigurationEntry setBytebinInstance(String bytebinInstance) {
        this.bytebinInstance = bytebinInstance;
        return this;
    }

    public ConfigurationEntry setImgflipUsername(String imgflipUsername) {
        this.imgflipUsername = imgflipUsername;
        return this;
    }

    public ConfigurationEntry setImgflipPassword(String imgflipPassword) {
        this.imgflipPassword = imgflipPassword;
        return this;
    }

    public ConfigurationEntry setWeatherApiKey(String weatherApiKey) {
        this.weatherApiKey = weatherApiKey;
        return this;
    }

    public ConfigurationEntry setSentryDsn(String sentryDsn) {
        this.sentryDsn = sentryDsn;
        return this;
    }

    public String getBotToken() {
        return botToken;
    }

    public String getCommandPrefix() {
        return commandPrefix;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getVerifiedRoleId() {
        return verifiedRoleId;
    }

    public String getVerificationInputChannelId() {
        return verificationInputChannelId;
    }

    public String getPrivateAuditLogChannelId() {
        return privateAuditLogChannelId;
    }

    public String getPublicAuditLogChannelId() {
        return publicAuditLogChannelId;
    }

    public String getModMailInputChannel() {
        return modMailInputChannel;
    }

    public String getModMailOutputChannel() {
        return modMailOutputChannel;
    }

    public String getMongoDbConnectionUri() {
        return mongoDbConnectionUri;
    }

    public String getMailGunApplicationKey() {
        return mailGunApplicationKey;
    }

    public String getMailGunBaseUrl() {
        return mailGunBaseUrl;
    }

    public String getVerificationEmailSubject() {
        return verificationEmailSubject;
    }

    public String getVerificationEmailSender() {
        return verificationEmailSender;
    }

    public String getVerificationEmailTemplateName() {
        return verificationEmailTemplateName;
    }

    public String getWolframAlphaApplicationId() {
        return wolframAlphaApplicationId;
    }

    public String getImgurClientId() {
        return imgurClientId;
    }

    public String getMessageLogChannel() {
        return messageLogChannel;
    }

    public String getBytebinInstance() {
        return bytebinInstance;
    }

    public String getImgflipUsername() {
        return imgflipUsername;
    }

    public String getImgflipPassword() {
        return imgflipPassword;
    }

    public String getWeatherApiKey() {
        return weatherApiKey;
    }

    public String getSentryDsn() {
        return sentryDsn;
    }
}
