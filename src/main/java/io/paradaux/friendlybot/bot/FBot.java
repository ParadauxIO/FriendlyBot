package io.paradaux.friendlybot.bot;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import io.paradaux.friendlybot.config.FConfiguration;
import io.paradaux.friendlybot.core.locale.LocaleLogger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class FBot {

    private final FConfiguration config;
    private final EventWaiter eventWaiter;
    private final JDA client;

    public FBot(FConfiguration config) throws LoginException {
        this.config = config;
        this.eventWaiter = new EventWaiter();
        this.client = login(config.getBotToken());
    }

    /**
     * Creates an Instance of JDA from the provided token.
     * @param token The Discord Token taken from the configuration file.
     * @return JDA An Instance of JDA
     * @throws LoginException When logging in proved unsuccessful.
     * */
    public JDA login(String token) throws LoginException {
        JDABuilder builder = JDABuilder.createDefault(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_REACTIONS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setBulkDeleteSplittingEnabled(false)
                .addEventListeners(eventWaiter, createCommandClient());

        if (token == null) {
            throw new LoginException("The Configuration File does not contain a token.");
        }

        return builder.build();
    }

    private CommandClient createCommandClient() {
        LocaleLogger.info("Initialising: CommandController");

        CommandClientBuilder builder = new CommandClientBuilder()
                .setPrefix(config.getCommandPrefix())
                .setOwnerId("150993042558418944")
                .setActivity(Activity.listening("modmail queries.."))
                .addCommands();

        return builder.build();
    }

    public JDA getClient() {
        return client;
    }
}
