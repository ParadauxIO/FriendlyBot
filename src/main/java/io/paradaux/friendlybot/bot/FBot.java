package io.paradaux.friendlybot.bot;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import io.paradaux.friendlybot.bot.command.CommandListener;
import io.paradaux.friendlybot.bot.commands.image.CatCommand;
import io.paradaux.friendlybot.core.data.config.FConfiguration;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class FBot {

    private final FConfiguration config;
    private final EventWaiter eventWaiter;
    private final CommandListener commandListener;
    private final JDA client;

    public FBot(FConfiguration config) throws LoginException {
        this.config = config;
        this.eventWaiter = new EventWaiter();
        this.commandListener = new CommandListener();
        registerCommands();
        this.client = login(config.getBotToken());
    }

    public void registerCommands() {
        commandListener.registerCommand(new CatCommand());
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
                .addEventListeners(eventWaiter, commandListener);

        if (token == null) {
            throw new LoginException("The Configuration File does not contain a token.");
        }

        return builder.build();
    }

    public JDA getClient() {
        return client;
    }
}
