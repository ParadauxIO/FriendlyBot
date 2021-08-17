package io.paradaux.friendlybot;

import io.paradaux.friendlybot.bot.FBot;
import io.paradaux.friendlybot.config.FConfiguration;
import io.paradaux.friendlybot.config.FConfigurationLoader;
import io.paradaux.friendlybot.core.cache.GuildCache;
import io.paradaux.friendlybot.core.locale.LocaleLogger;
import io.paradaux.friendlybot.core.locale.LocaleManager;
import org.slf4j.LoggerFactory;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.serialize.SerializationException;

import javax.security.auth.login.LoginException;

public class FBApplication {

    private static LocaleManager localeManager;
    private static LocaleLogger logger;
    private static FConfiguration config;
    private static FBot bot;
    private static GuildCache guildCache;


    public static void main(String[] args) throws ConfigurateException, LoginException {
        localeManager = new LocaleManager();
        logger = new LocaleLogger(LoggerFactory.getLogger(FBApplication.class), true);

        config = FConfigurationLoader.loadConfig();
        bot = new FBot(config);
        guildCache = new GuildCache(bot.getClient());
    }



}
