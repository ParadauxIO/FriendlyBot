package io.paradaux.csbot.api;

import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {

    private static Logger logger;
    public static Logger getLogger() { return logger; }

    public Logging() {
        logger = createLogger();
    }

    public Logger createLogger() {
        return LoggerFactory.getLogger("CSBot");
    }

}
