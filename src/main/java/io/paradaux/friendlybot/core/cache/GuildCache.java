package io.paradaux.friendlybot.core.cache;

import io.paradaux.friendlybot.managers.GuildSettingsManager;
import io.paradaux.friendlybot.models.FGuild;
import io.paradaux.friendlybot.utils.models.database.GuildSettingsEntry;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class GuildCache {

    /**
     * Map of Guild IDs (Snowflake) to FGuild objects.
     * */
    private final Map<String, FGuild> guilds = new HashMap<>();
    private final JDA client;

    public GuildCache(JDA client) {
        this.client = client;
    }

    public boolean isCached(String id) {
        return guilds.containsKey(id);
    }

    /**
     * Returns an FGuild, directly from the cache if available, otherwise it's grabbed from the database/client.
     * */
    @NotNull
    public FGuild getGuild(String id) {
        // If the guild is cached
        if (isCached(id)) {
            return guilds.get(id);
        }

        return resetGuild(id);
    }

    /**
     * Returns an FGuild, skipping the cache.
     * */
    @NotNull
    public FGuild retrieveGuild(String id) {
        GuildSettingsEntry entry = GuildSettingsManager.getInstance().getGuild(id);

        Guild guild = client.getGuildById(id);
        if (guild == null) {
            throw new IllegalArgumentException("The provided guild does not exist.");
        }

        return new FGuild(guild, entry);
    }

    /**
     * Resets the version in the cache.
     * */
    public FGuild resetGuild(String id) {
        FGuild guild = retrieveGuild(id);
        guilds.put(id, guild);
        return guild;
    }
}
