package io.paradaux.friendlybot.models;

import io.paradaux.friendlybot.utils.models.database.GuildSettingsEntry;
import net.dv8tion.jda.api.entities.Guild;

public class FGuild {

    private final Guild guild;
    private final GuildSettingsEntry settings;

    public FGuild(Guild guild, GuildSettingsEntry settings) {
        this.guild = guild;
        this.settings = settings;
    }

    public Guild getGuild() {
        return guild;
    }

    public GuildSettingsEntry getSettings() {
        return settings;
    }
}
