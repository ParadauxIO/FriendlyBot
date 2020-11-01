package io.paradaux.csbot;

import io.paradaux.csbot.api.ConfigurationCache;
import io.paradaux.csbot.api.ConfigurationUtils;
import io.paradaux.csbot.api.Logging;
import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

/**
 * CSBot is the main (executable) class for the project. It provides a set of lazy dependency injection instances
 *
 * @author Rían Errity
 * @version Last Modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 * */

public class CSBot {

    private static JDA jda;
    public static JDA getJda() { return jda; }

    private static ConfigurationCache configurationCache;
    public static ConfigurationCache getConfigurationCache() { return configurationCache; }

    public static void main(String[] args) {

        // Instantiate Logger instance.
        Logging logging = new Logging();
        Logger logger = Logging.getLogger();

        // Version Information
        System.out.println("CSBot v0.1.0 - Maintained by Rían Errity <rian@paradaux.io>");

        // Prepare Configuration File/Cache
        ConfigurationUtils.deployConfiguration();
        try {
            configurationCache = ConfigurationUtils.readConfigurationFile();
        } catch (FileNotFoundException exception) {
            logger.error("Could not find the configuration file!", exception);
        }

    }

}
