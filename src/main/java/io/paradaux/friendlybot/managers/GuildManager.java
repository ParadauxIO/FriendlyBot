package io.paradaux.friendlybot.managers;

import io.paradaux.friendlybot.core.utils.models.database.GuildSettingsEntry;
import org.slf4j.Logger;

import java.util.HashMap;

public class GuildManager {

    private final HashMap<String, GuildSettingsEntry> settingsProfile;
    private final Logger logger;

    public GuildManager(Logger logger) {
        this.settingsProfile = new HashMap<>();
        logger.info("Initialising: Guild Manager");
        this.logger = logger;
    }

}
