package io.paradaux.csbot.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This Class provides various SLF4J Wrappers/Bindings for use throughout the application.
 *
 * @author Rían Errity
 * @version Last modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 * @see io.paradaux.csbot.CSBot
 * */

public class Logging {

    public static Logging INSTANCE;

    private static Logger logger;
    public static Logger getLogger() { return logger; }

    public Logging() {
        logger = createLogger();
        INSTANCE = this;
    }

    public Logger createLogger() {
        return LoggerFactory.getLogger("CSBot");
    }

}
