package io.paradaux.friendlybot.utils.models.database;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.HashMap;

public class GuildSettingsEntry implements Serializable {

    protected static final long serialVersionUID = 1L;

    @BsonProperty(value = "guild_id")
    private String guildId;

    @BsonProperty(value = "verification_role_id")
    private String verificationRoleId;

    @BsonProperty(value = "verification_input_channel")
    private String verificationInputChannel;

    @BsonProperty(value = "mod_audit_log_channel")
    private String privateAuditLogChannel;

    @BsonProperty(value = "audit_log_channel")
    private String publicAuditLogChannel;

    @BsonProperty(value = "modmail_input_channel")
    private String modmailInputChannel;

    @BsonProperty(value = "modmail_output_channel")
    private String modmailOutputChannel;

    @BsonProperty(value = "message_log_channel")
    private String messageLogChannel;

    @BsonProperty(value = "moderators")
    private HashMap<String, String> moderators;

    @BsonProperty(value = "administrators")
    private HashMap<String, String> administrators;

    public GuildSettingsEntry() {
        
    }

    public GuildSettingsEntry(String guildId, String verificationRoleId, String verificationInputChannel, String privateAuditLogChannel,
                              String publicAuditLogChannel, String modmailInputChannel, String modmailOutputChannel,
                              String messageLogChannel) {
        this.guildId = guildId;
        this.verificationRoleId = verificationRoleId;
        this.verificationInputChannel = verificationInputChannel;
        this.privateAuditLogChannel = privateAuditLogChannel;
        this.publicAuditLogChannel = publicAuditLogChannel;
        this.modmailInputChannel = modmailInputChannel;
        this.modmailOutputChannel = modmailOutputChannel;
        this.messageLogChannel = messageLogChannel;
    }

    public GuildSettingsEntry setGuildId(String guildId) {
        this.guildId = guildId;
        return this;
    }

    public GuildSettingsEntry setVerificationRoleId(String verificationRoleId) {
        this.verificationRoleId = verificationRoleId;
        return this;
    }

    public GuildSettingsEntry setVerificationInputChannel(String verificationInputChannel) {
        this.verificationInputChannel = verificationInputChannel;
        return this;
    }

    public GuildSettingsEntry setPrivateAuditLogChannel(String privateAuditLogChannel) {
        this.privateAuditLogChannel = privateAuditLogChannel;
        return this;
    }

    public GuildSettingsEntry setPublicAuditLogChannel(String publicAuditLogChannel) {
        this.publicAuditLogChannel = publicAuditLogChannel;
        return this;
    }

    public GuildSettingsEntry setModmailInputChannel(String modmailInputChannel) {
        this.modmailInputChannel = modmailInputChannel;
        return this;
    }

    public GuildSettingsEntry setModmailOutputChannel(String modmailOutputChannel) {
        this.modmailOutputChannel = modmailOutputChannel;
        return this;
    }

    public GuildSettingsEntry setMessageLogChannel(String messageLogChannel) {
        this.messageLogChannel = messageLogChannel;
        return this;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getVerificationRoleId() {
        return verificationRoleId;
    }

    public String getVerificationInputChannel() {
        return verificationInputChannel;
    }

    public String getPrivateAuditLogChannel() {
        return privateAuditLogChannel;
    }

    public String getPublicAuditLogChannel() {
        return publicAuditLogChannel;
    }

    public String getModmailInputChannel() {
        return modmailInputChannel;
    }

    public String getModmailOutputChannel() {
        return modmailOutputChannel;
    }

    public String getMessageLogChannel() {
        return messageLogChannel;
    }
}
