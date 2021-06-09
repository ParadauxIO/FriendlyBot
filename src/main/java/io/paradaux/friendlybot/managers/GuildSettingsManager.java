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
        instance.logger.info("Getting an instance of GuildSettingsManager...");

        return instance;
    }

    public GuildSettingsEntry getGuild(String guildId) {
        // Check if it's cached
        GuildSettingsEntry guild = cachedGuilds.get(guildId);

        if (guild != null) {
            return guild;
        }

        // If it's not cached try to pull it from the database
        logger.info("Guild did not exist in the cache");
        guild = guilds.find(Filters.eq("guild_id", guildId)).first();

        // if it's not in the database create a new profile.
        if (guild == null) {
            guild = createNewProfile(guildId);
        }

        // Load the guild into the cache
        cachedGuilds.put(guildId, guild);

        // Return the guild after it has been loaded into the cache
        return guild;
    }

    public GuildSettingsEntry createNewProfile(String guildId) {
        logger.info("Creating a guild profile for {}", guildId);

        // Create an empty guild with just the ID
        GuildSettingsEntry entry = new GuildSettingsEntry()
                .setGuildId(guildId);

        // Insert the newly created guild back into the database.
        guilds.insertOne(entry);
        return entry;
    }

    public void updateProfile(GuildSettingsEntry entry) {
        // Updates it in the cache
        cachedGuilds.put(entry.getGuildId(), entry);

        // Updates it in the database
        if (!guildExists(entry.getGuildId())) {
            guilds.insertOne(entry);
        } else {
            guilds.findOneAndReplace(Filters.eq("guild_id", entry.getGuildId()), entry);
        }
    }

    public boolean guildExists(String guildId) {
        // Checks first to see if it's in the cache
        GuildSettingsEntry entry = cachedGuilds.get(guildId);

        // If it isn't, check the database
        if (entry == null) {
            entry = guilds.find(Filters.eq("guild_id", guildId)).first();
        }

        // Return whether or not we found it.
        return entry != null;
    }

    public void removeProfile(String guildId) {
        // Removes a guild from the cache
        cachedGuilds.remove(guildId);

        // Removes a guild from the database
        guilds.findOneAndDelete(Filters.eq("guild_id", guildId));
    }

}
