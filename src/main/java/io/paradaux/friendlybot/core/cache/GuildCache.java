package io.paradaux.friendlybot.core.cache;

import io.paradaux.friendlybot.core.data.database.models.FGuild;
import io.paradaux.friendlybot.core.data.database.models.query.QFGuild;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class GuildCache {

    private static GuildCache instance;

    public static GuildCache getInstance() {
        return instance;
    }

    /**
     * Map of Guild IDs (Snowflake) to FGuild objects.
     * */
    private final Map<String, FGuild> guilds = new HashMap<>();
    private final JDA client;

    public GuildCache(JDA client) {
        this.client = client;
        GuildCache.instance = this;
    }

    public boolean isCached(String id) {
        return guilds.containsKey(id);
    }

    /**
     * Loads guilds into the cache
     * */
    public void load() {
        for (Guild g : client.getGuilds()) {
            resetGuild(g.getId());
        }
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
        Guild guild = client.getGuildById(id);
        if (guild == null) {
            throw new IllegalArgumentException("The provided guild does not exist.");
        }

        FGuild fGuild = new QFGuild().guildId.equalTo(id).findOne();
        if (fGuild == null) {
            // TODO Create new guild profile.
            throw new IllegalStateException();
        }

        fGuild.setGuild(guild);

        //return new FGuild(guild, entry);
        return fGuild; // TODO
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
