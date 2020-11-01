package io.paradaux.csbot.listeners;

import io.paradaux.csbot.api.ConfigurationCache;
import io.paradaux.csbot.api.Logging;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;

/**
 * This event is used to show the bot has successfully logged in, and to provide usage information.
 *
 * @author Rían Errity
 * @version Last modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 * @see io.paradaux.csbot.CSBot
 * */

public class ReadyListener extends ListenerAdapter {

    ConfigurationCache configurationCache;
    Logger logger;

    public ReadyListener(ConfigurationCache configurationCache) {
        this.configurationCache = configurationCache;
        logger = Logging.getLogger();
    }

    @Override
    public void onReady(ReadyEvent event) {

        int guildCount = event.getGuildAvailableCount();
        int userCount = 0;

        for (Guild guild : event.getJDA().getGuilds()) {
            userCount += guild.getMembers().size();
        }

        logger.info("Currently serving {} users in {} guilds", userCount, guildCount);
    }
}
