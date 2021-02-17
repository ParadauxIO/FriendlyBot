package io.paradaux.friendlybot.listeners.ai;

import io.paradaux.friendlybot.managers.AIManager;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.types.DiscordEventListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;

import java.util.Random;

public class ResponseListener extends DiscordEventListener {

    private static final String TRIGGER_WORD = "friendlybot";

    private final Random random;

    public ResponseListener(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.random = new Random();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String messageContent = event.getMessage().getContentRaw();
        AIManager ai = AIManager.getInstance();

        if (event.getAuthor().isBot()) {
            return;
        }

        if (ai.isIgnored(event.getAuthor().getId())) {
            return;
        }

        String response = null;
        if (event.getMessage().getContentRaw().toLowerCase().contains(TRIGGER_WORD)) {
            response = ai.generateMessage(pickRandomWord(messageContent));
            event.getChannel().sendMessage(response).queue();
        }

        if (response != null) {
            ai.addMessage(response);
        }

    }

    public String pickRandomWord(String sentence) {
        String[] words = sentence.split(" ");
        return words[random.nextInt(words.length)];
    }

}
