package io.paradaux.friendlybot.data.database.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GuildSettings {

    @Id
    String guildId;

    String guildName;

    public String getGuildId() {
        return guildId;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    @Override
    public String toString() {
        return "GuildSettings{" + "guildId='" + guildId + '\'' + ", guildName='" + guildName + '\'' + '}';
    }
}
