package io.paradaux.friendlybot.discord.listeners.rooms;

import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.DiscordEventListener;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import org.slf4j.Logger;

public class RoomLeaveListener extends DiscordEventListener {

    public RoomLeaveListener(ConfigurationEntry config, Logger logger) {
        super(config, logger);
    }

    public void onGuildVoiceLeaveEvent(GuildVoiceLeaveEvent event) {
        if (!event.getChannelLeft().getName().startsWith("Room")) {
            return;
        }

        if (event.getChannelLeft().getMembers().size() == 0 && !event.getChannelLeft().getName().equals("Room 1")) {
            // Delete the room
        }

    }

}
