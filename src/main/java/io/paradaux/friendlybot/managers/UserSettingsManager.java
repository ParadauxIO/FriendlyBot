package io.paradaux.friendlybot.managers;

import com.mongodb.client.MongoCollection;
import io.paradaux.friendlybot.core.utils.TimeUtils;
import io.paradaux.friendlybot.core.utils.models.database.UserSettingsEntry;
import io.paradaux.friendlybot.core.utils.models.exceptions.ManagerNotReadyException;
import net.dv8tion.jda.api.entities.User;
import org.bson.Document;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserSettingsManager {

    private static final short COOLDOWN = 3;

    private static UserSettingsManager instance;

    private final Logger logger;

    private final MongoCollection<UserSettingsEntry> settings;

    public UserSettingsManager(Logger logger, MongoManager mongo) {
        this.logger = logger;
        instance = this;
        logger.info("Initialising: User Settings Manager");
        settings = mongo.getUserSettings();
    }

    public static UserSettingsManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

    public UserSettingsEntry createNewProfile(User user, String guildId) {
        logger.info("Creating profile for: " + user.getAsTag());
        UserSettingsEntry entry = new UserSettingsEntry()
                .setGuildId(guildId)
                .setDoTrainAi(true)
                .setDiscordId(user.getId())
                .setFirstSavedDiscordTag(user.getAsTag());

        settings.insertOne(entry);

        return entry;
    }

    public long getProfileCountByColor(String guildId, String color) {
        return settings.countDocuments(new Document().append("guild_id", guildId).append("custom_color_role", color));
    }

    public UserSettingsEntry getProfileById(String guildId, String userId) {
        UserSettingsEntry entry = settings.find(getGuildUserSearchQuery(guildId, userId)).first();

        if (entry == null) {
            entry = createNewProfile(DiscordBotManager.getInstance().getUser(userId), guildId);
        }

        return entry;
    }

    public void updateSettingsProfile(UserSettingsEntry entry) {
        settings.findOneAndReplace(getGuildUserSearchQuery(entry.getGuildId(), entry.getDiscordId()), entry);
    }

    public boolean hasCooldownElapsed(UserSettingsEntry entry) {
        return TimeUtils.getDaysBetween(entry.getLastSetColor(), new Date()) >= COOLDOWN;
    }

    private Document getGuildUserSearchQuery(String guildId, String discordId) {
        return new Document()
                .append("guild_id", guildId)
                .append("discord_id", discordId);
    }

    public List<String> getIgnoredUsers() {
        final List<String> ignoredUsers = new ArrayList<>();

        for (var user : settings.find()) {
            if (!user.isDoTrainAi()) {
                ignoredUsers.add(user.getDiscordId());
            }
        }

        return ignoredUsers;
    }

}
