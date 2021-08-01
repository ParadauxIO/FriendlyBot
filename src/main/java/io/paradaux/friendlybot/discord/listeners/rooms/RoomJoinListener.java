package io.paradaux.friendlybot.discord.listeners.rooms;

import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.DiscordEventListener;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import org.slf4j.Logger;

public class RoomJoinListener extends DiscordEventListener {

    public RoomJoinListener(ConfigurationEntry config, Logger logger) {
        super(config, logger);
    }

    public void onGuildVoiceJoinEvent(GuildVoiceJoinEvent event) {
        if (!event.getChannelJoined().getName().startsWith("Room")) {
            return;
        }

        if (event.getChannelJoined().getMembers().size() == 1) {
            Guild guild = event.getGuild();
            guild.createVoiceChannel("Room " + getLastRoomNumber(guild), event.getChannelJoined().getParent()).queue();
            // Create a new room
        }

    }

    public int getLastRoomNumber(Guild guild) {
        int i = 0;
        int lastFound = 0;
        for (VoiceChannel channel : guild.getVoiceChannels()) {
            if (channel.getName().contains("Room " + i)) {
                lastFound = i;
            }
            i++;
        }

        return lastFound;
    }

}
