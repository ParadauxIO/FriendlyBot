package io.paradaux.friendlybot.discord.integrity.listeners;

import io.paradaux.friendlybot.core.cache.GuildCache;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.DiscordEventListener;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class GuildCreationEvent extends DiscordEventListener {

    public GuildCreationEvent(ConfigurationEntry config, Logger logger) {
        super(config, logger);
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {

    }
}
