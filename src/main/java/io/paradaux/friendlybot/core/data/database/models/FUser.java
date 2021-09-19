package io.paradaux.friendlybot.core.data.database.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "users")
public class FUser {

    @Id
    private long id;

    @ManyToOne(optional = false)
    private FGuild guild;

    private String guildId;
    private String userId;

    private String customColorRole;
    private String discordTag;
    private Date lastChangedColor;

    public long getId() {
        return id;
    }

    public FGuild getGuild() {
        return guild;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getUserId() {
        return userId;
    }

    public String getCustomColorRole() {
        return customColorRole;
    }

    public String getDiscordTag() {
        return discordTag;
    }

    public Date getLastChangedColor() {
        return lastChangedColor;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setGuild(FGuild guild) {
        this.guild = guild;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCustomColorRole(String customColorRole) {
        this.customColorRole = customColorRole;
    }

    public void setDiscordTag(String discordTag) {
        this.discordTag = discordTag;
    }

    public void setLastChangedColor(Date lastChangedColor) {
        this.lastChangedColor = lastChangedColor;
    }
}
