package io.paradaux.friendlybot.managers;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import io.paradaux.friendlybot.utils.TimeUtils;
import io.paradaux.friendlybot.utils.models.database.UserSettingsEntry;
import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.bson.Document;
import org.slf4j.Logger;

import java.util.Date;

import static com.mongodb.client.model.Filters.eq;

public class SettingsManager {

    private static final short COOLDOWN = 3;

    private static SettingsManager instance;

    private final Logger logger;
    private final MongoManager mongo;

    private final MongoCollection<UserSettingsEntry> settings;

    public SettingsManager(Logger logger, MongoManager mongo) {
        this.logger = logger;
        this.mongo = mongo;
        instance = this;
        settings = mongo.getUserSettings();
    }

    public static SettingsManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

    public UserSettingsEntry createNewProfile(Member member) {
        return createNewProfile(member.getUser(), member.getGuild().getId());
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
        return settings.countDocuments(new Document().append("guild_id", color).append("custom_color_role", color));
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

    public void updateColorCooldown(String guildId, String userId) {
        UserSettingsEntry entry = getProfileById(guildId, userId)
                .setLastSetColor(new Date());

    }

    public boolean hasCooldownElapsed(UserSettingsEntry entry) {
        return TimeUtils.getDaysBetween(entry.getLastSetColor(), new Date()) >= COOLDOWN;
    }

    private Document getGuildUserSearchQuery(String guildId, String discordId) {
        return new Document()
                .append("guild_id", guildId)
                .append("discord_id", discordId);
    }

}
