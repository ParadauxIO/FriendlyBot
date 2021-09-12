package io.paradaux.friendlybot;

import io.ebean.DB;
import io.paradaux.friendlybot.bot.FBot;
import io.paradaux.friendlybot.core.data.config.FConfiguration;
import io.paradaux.friendlybot.core.data.config.FConfigurationLoader;
import io.paradaux.friendlybot.core.cache.GuildCache;
import io.paradaux.friendlybot.core.data.database.EBeanConnection;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import io.paradaux.friendlybot.core.locale.LocaleLogger;
import io.paradaux.friendlybot.core.locale.LocaleManager;
import org.slf4j.LoggerFactory;
import org.spongepowered.configurate.ConfigurateException;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;

public class FBApplication {

    private static LocaleManager localeManager;
    private static LocaleLogger logger;
    private static FConfiguration config;
    private static FBot bot;
    private static GuildCache guildCache;
    private static EBeanConnection eBeanConnection;


    public static void main(String[] args) throws ConfigurateException, LoginException {
        localeManager = new LocaleManager();
        logger = new LocaleLogger(LoggerFactory.getLogger(FBApplication.class), true);

        config = FConfigurationLoader.loadConfig();
        eBeanConnection = new EBeanConnection(config);
        bot = new FBot(config);
        guildCache = new GuildCache(bot.getClient());
    }



}
