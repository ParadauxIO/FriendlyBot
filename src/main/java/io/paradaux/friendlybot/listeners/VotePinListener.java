package io.paradaux.friendlybot.listeners;

import io.paradaux.friendlybot.utils.models.types.DiscordEventListener;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class VotePinListener extends DiscordEventListener {

    private static final int PIN_THRESHOLD = 4;
    private static final String PIN_EMOJI = "📌";

    private final Logger logger;

    public VotePinListener(Logger logger) {
        super(logger);
        this.logger = logger;
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        event.getChannel().retrieveMessageById(event.getMessageId()).queue((message) -> {
            int pinCount = 0;
            for (MessageReaction reaction : message.getReactions()) {
                if (reaction.getReactionEmote().isEmoji() && reaction.getReactionEmote().getEmoji().equals(PIN_EMOJI)) {
                    pinCount = reaction.getCount();
                    break;
                }
            }

            logger.info(String.valueOf(pinCount));

            if (pinCount >= PIN_THRESHOLD && !message.isPinned()) {
                message.pin().queue();
            }
        });
    }
}
