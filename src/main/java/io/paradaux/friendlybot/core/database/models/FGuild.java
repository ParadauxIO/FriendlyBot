package io.paradaux.friendlybot.core.database.models;

import io.ebean.Model;
import io.paradaux.friendlybot.core.cache.GuildCache;
import net.dv8tion.jda.api.entities.Guild;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Entity
public class FGuild extends Model {

    @Id
    private long id;

    @Transient
    @Nullable
    private Guild guild;

    String guildId;

    private String verificationRoleId;
    private String verificationInputId;

    private String auditLogId;
    private String modAuditLogId;

    private String modMailInId;
    private String modMailOutId;

    private String messageLogId;

    private List<String> moderators;
    private List<String> administrators;

    private Integer lastIncidentId;
    private Integer lastTicketId;

    public FGuild() {

    }

    public FGuild(@Nullable Guild guild) {
        this.guild = guild;
    }

    public long getId() {
        return id;
    }

    @Nullable
    public Guild getGuild() {
        if (guild == null) {
            //guild = GuildCache.getInstance().getGuild(guildId);
        }
        return guild;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getVerificationRoleId() {
        return verificationRoleId;
    }

    public String getVerificationInputId() {
        return verificationInputId;
    }

    public String getAuditLogId() {
        return auditLogId;
    }

    public String getModAuditLogId() {
        return modAuditLogId;
    }

    public String getModMailInId() {
        return modMailInId;
    }

    public String getModMailOutId() {
        return modMailOutId;
    }

    public String getMessageLogId() {
        return messageLogId;
    }

    public List<String> getModerators() {
        return moderators;
    }

    public List<String> getAdministrators() {
        return administrators;
    }

    public Integer getLastIncidentId() {
        return lastIncidentId;
    }

    public Integer getLastTicketId() {
        return lastTicketId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setGuild(@Nullable Guild guild) {
        this.guild = guild;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public void setVerificationRoleId(String verificationRoleId) {
        this.verificationRoleId = verificationRoleId;
    }

    public void setVerificationInputId(String verificationInputId) {
        this.verificationInputId = verificationInputId;
    }

    public void setAuditLogId(String auditLogId) {
        this.auditLogId = auditLogId;
    }

    public void setModAuditLogId(String modAuditLogId) {
        this.modAuditLogId = modAuditLogId;
    }

    public void setModMailInId(String modMailInId) {
        this.modMailInId = modMailInId;
    }

    public void setModMailOutId(String modMailOutId) {
        this.modMailOutId = modMailOutId;
    }

    public void setMessageLogId(String messageLogId) {
        this.messageLogId = messageLogId;
    }

    public void setModerators(List<String> moderators) {
        this.moderators = moderators;
    }

    public void setAdministrators(List<String> administrators) {
        this.administrators = administrators;
    }

    public void setLastIncidentId(Integer lastIncidentId) {
        this.lastIncidentId = lastIncidentId;
    }

    public void setLastTicketId(Integer lastTicketId) {
        this.lastTicketId = lastTicketId;
    }
}
