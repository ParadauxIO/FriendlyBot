package io.paradaux.friendlybot.managers;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.database.TagEntry;
import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import org.bson.Document;
import org.slf4j.Logger;

public class TagManager {

    private static TagManager instance;

    private final ConfigurationEntry config;
    private final Logger logger;

    private final MongoCollection<TagEntry> tags;

    public TagManager(ConfigurationEntry config, Logger logger, MongoManager mongo) {
        this.config = config;
        this.logger = logger;
        this.tags = mongo.getTags();
        instance = this;
    }

    public static TagManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

    public void addTag(TagEntry entry) {
        tags.insertOne(entry);
    }

    public void removeTag(TagEntry entry) {
        tags.findOneAndDelete(getTagSearchQueryById(entry.getGuild(), entry.getId()));
    }

    public TagEntry getTagById(String guildId, String tagName) {
        return tags.find(getTagSearchQueryById(guildId, tagName)).first();
    }

    public TagEntry getTagByOwner(String guildId, String discordId) {
        return tags.find(getTagSearchQueryByOwner(guildId, discordId)).first();
    }

    private Document getTagSearchQueryById(String guildId, String tagName) {
        return new Document()
                .append("guild_id", guildId)
                .append("id", tagName);
    }

    private Document getTagSearchQueryByOwner(String guildId, String discordId) {
        return new Document()
                .append("guild_id", guildId)
                .append("discord_id", discordId);
    }
}
