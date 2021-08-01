package io.paradaux.friendlybot.discord.listeners;

import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.DiscordEventListener;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class InsultListener extends DiscordEventListener {

    private static final String INSULT_API = "https://insult.mattbas.org/api/insult";
    private static final List<String> targetUsers = new ArrayList<>();

    public InsultListener(ConfigurationEntry config, Logger logger) {
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

        if (!targetUsers.contains(event.getAuthor().getId())) {
            return;
        }

        HttpApi http = new HttpApi(getLogger());

        HttpRequest request = http.plainRequest(INSULT_API);
        http.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept((response -> {
            event.getAuthor().openPrivateChannel().queue((channel) -> {
                channel.sendMessage(response.body()).queue();
            });

        })).join();

    }

    public static void addUser(String id) {
        targetUsers.add(id);
    }

    public static void removeUser(String id) {
        targetUsers.remove(id);
    }

    public static List<String> getTargetUsers() {
        return targetUsers;
    }
}
