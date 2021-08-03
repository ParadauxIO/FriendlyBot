package io.paradaux.friendlybot;

import io.paradaux.friendlybot.bot.FBot;
import io.paradaux.friendlybot.config.FConfiguration;
import io.paradaux.friendlybot.config.FConfigurationLoader;
import io.paradaux.friendlybot.core.cache.GuildCache;
import org.spongepowered.configurate.serialize.SerializationException;

import javax.security.auth.login.LoginException;

public class FBApplication {

    private static FConfiguration config;
    private static FBot bot;
    private static GuildCache guildCache;


    public static void main(String[] args) throws SerializationException, LoginException {
        config = FConfigurationLoader.loadConfig();
        bot = new FBot(config);
        guildCache = new GuildCache(bot.getClient());
    }

}
