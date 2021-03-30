package io.paradaux.friendlybot.managers;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import io.paradaux.friendlybot.utils.models.database.GuildSettingsEntry;
import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import org.slf4j.Logger;

import java.util.HashMap;

public class GuildSettingsManager {

    private static final HashMap<String, GuildSettingsEntry> cachedGuilds = new HashMap<>();
    private static GuildSettingsManager instance;

    private final MongoCollection<GuildSettingsEntry> guilds;
    private final Logger logger;

    public GuildSettingsManager(Logger logger, MongoManager mongo) {
        logger.info("Initialising: Guild Settings Manager");
        this.logger = logger;
        this.guilds = mongo.getGuildSettings();
        instance = this;
    }

    public static GuildSettingsManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

    public GuildSettingsEntry getGuild(String guildId) {
        // Check if it's cached
        GuildSettingsEntry guild = cachedGuilds.get(guildId);

        // If it's not cached try to pull it from the database
        if (guild == null) {
            guild = guilds.find(Filters.eq("guild_id", guildId)).first();
            cachedGuilds.put(guildId, guild);
        }

        // if it's not in the database create a new profile.
        if (guild == null) {
            guild = createNewProfile(guildId);
            cachedGuilds.put(guildId, guild);
        }

        return guild;
    }

    public GuildSettingsEntry createNewProfile(String guildId) {
        GuildSettingsEntry entry = new GuildSettingsEntry()
                .setGuildId(guildId);
        guilds.insertOne(entry);
        return entry;
    }

    public void updateProfile(GuildSettingsEntry entry) {
        guilds.findOneAndReplace(Filters.eq("guild_id", entry.getGuildId()), entry);
    }

    public void removeProfile(String guildId) {
        guilds.findOneAndDelete(Filters.eq("guild_id", guildId));
    }

}
