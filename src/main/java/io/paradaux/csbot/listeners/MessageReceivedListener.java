package io.paradaux.csbot.listeners;

import io.paradaux.csbot.api.ConfigurationCache;
import io.paradaux.csbot.api.Logging;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * This event handles the parsing of email addresses within the listener channel, and calls for verification emails to be sent.
 *
 * @author Rían Errity
 * @version Last modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 * @see io.paradaux.csbot.CSBot
 * */

public class MessageReceivedListener extends ListenerAdapter {

    ConfigurationCache configurationCache;
    Logger logger;

    public MessageReceivedListener(ConfigurationCache configurationCache) {
        this.configurationCache = configurationCache;
        logger = Logging.getLogger();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

    }
}
