package io.paradaux.friendlybot.managers;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import io.paradaux.friendlybot.utils.models.database.UserSettingsEntry;
import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.slf4j.Logger;

import static com.mongodb.client.model.Filters.eq;

public class SettingsManager {

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

    public void updateProfiles(DiscordBotManager discord) {
        System.out.println("Creating profiles");
        for (Guild guild : discord.getClient().getGuilds()) {
            System.out.println("Guild: " + guild.getName());
            for (Member member : guild.getMembers()) {
                System.out.println("Member: " + member.getEffectiveName());
                logger.info("Creating profile for: " + member.getEffectiveName());
                createNewProfile(member);
            }
        }
    }


    public void createNewProfile(Member member) {
        UserSettingsEntry entry = new UserSettingsEntry()
                .setGuildId(member.getGuild().getId())
                .setDoTrainAi(true)
                .setDiscordId(member.getId())
                .setFirstSavedDiscordTag(member.getUser().getAsTag());

        settings.insertOne(entry);
    }

    public FindIterable<UserSettingsEntry> getProfilesByColor(String color) {
        return settings.find(eq("custom_color_role", color));
    }

    public UserSettingsEntry getProfileById(String guildId, String userId) {
        for (var potEntry : settings.find(eq("discord_id", userId))) {
            if (potEntry.getGuildId().equals(guildId)) {
                return potEntry;
            }
        }

        return null;
    }

}
