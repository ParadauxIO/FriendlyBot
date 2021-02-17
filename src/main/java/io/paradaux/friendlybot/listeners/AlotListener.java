package io.paradaux.friendlybot.listeners;

import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.types.DiscordEventListener;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.List;
import java.util.regex.Pattern;

public class AlotListener extends DiscordEventListener {

    private static final String ALOT_EMOT_ID = "806892779233345546";
    private static final Pattern ALOT = Pattern.compile("(?:^|\\W)alot(?:$|\\W)");

    public AlotListener(ConfigurationEntry config, Logger logger) {
        super(config, logger);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getChannelType() == ChannelType.PRIVATE) {
            return;
        }

        if (event.getAuthor().isBot()) {
            return;
        }

        if (ALOT.matcher(message.getContentRaw()).results().count() >= 1) {
            event.getGuild().retrieveEmotes().queue((listedEmotes -> {
                Emote alot = null;

                for (var emote : listedEmotes) {
                    if (emote.getName().equals("alot")) {
                        alot = emote;
                        break;
                    }
                }

                if (alot == null) {
                    throw new RuntimeException("The alot emoji does not exist for the guild.");
                }

                message.addReaction(alot).queue();
            }));
        }

    }
}
