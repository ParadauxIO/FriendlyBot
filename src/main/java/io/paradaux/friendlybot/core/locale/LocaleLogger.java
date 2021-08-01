package io.paradaux.friendlybot.core.locale;

import org.slf4j.Logger;

/**
 * A wrapper on top of SLF4J which allows for the logging of system messages using locale entries to provide easier internationalisation.
 * @author Rían Errity
 * */
public class LocaleLogger {

    private static Logger logger;
    private static boolean debug;

    public LocaleLogger(Logger logger, boolean debug) {
        LocaleLogger.logger = logger;
        LocaleLogger.debug = debug;
    }

    public static void toggleDebug() {
        debug = !debug;
    }

    public static void info(String str, String... args) {
        logger.info(LocaleManager.get(str), (Object[]) args);
    }

    public static void warn(String str, String... args) {
        logger.warn(LocaleManager.get(str), (Object[]) args);
    }

    public static void debug(String str, String... args) {
        if (debug) {
            logger.debug(LocaleManager.get(str), (Object[]) args);
        }
    }

    public static void error(String str, String... args) {
        logger.error(str, (Object[]) args);
    }
}
