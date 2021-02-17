package io.paradaux.friendlybot.listeners.ai;

import io.paradaux.friendlybot.managers.AIManager;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.utils.models.database.MessageEntry;
import io.paradaux.friendlybot.utils.models.types.DiscordEventListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class TrainingListener extends DiscordEventListener {

    private final Logger logger;
    private final MongoManager mongo;

    private static final ArrayList<String> ignoredChannels = new ArrayList<>();

    static {
        Collections.addAll(ignoredChannels, "758361690885718096", "806368435801161738", "773209534352457738", "806368598532161537",
                "773541543164117013", "772589960384741396");
    }

    public TrainingListener(Logger logger, MongoManager mongo) {
        super(logger);
        this.logger = logger;
        this.mongo = mongo;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String messageContent = event.getMessage().getContentRaw();
        String msgLwrCase = messageContent.toLowerCase();
        AIManager ai = AIManager.getInstance();

        if (event.getAuthor().isBot()) {
            return;
        }

        if (ignoredChannels.contains(event.getChannel().getId())) {
            return;
        }

        if (ai.isIgnored(event.getAuthor().getId())) {
            return;
        }

        if (messageContent.startsWith("!") || messageContent.startsWith(";") || messageContent.startsWith("$")) {
            return;
        }

        if (msgLwrCase.contains("friendlybot")) {
            return;
        }

        MessageEntry entry = new MessageEntry()
                .setAuthorId(event.getAuthor().getId())
                .setMessageId(event.getMessageId())
                .setContent(event.getMessage().getContentRaw())
                .setChannel(event.getChannel().getId())
                .setDate(new Date());

        ai.storeMessage(entry);
        ai.addMessage(entry.getContent());
    }

}
