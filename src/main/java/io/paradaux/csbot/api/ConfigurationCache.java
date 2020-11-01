package io.paradaux.csbot.api;

import java.util.List;

/**
 * Contains a cached copy of the configuration file's contents, to allow easy access within the application
 *
 * @author Rían Errity
 * @version Last modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 * @see ConfigurationUtils
 * */

public class ConfigurationCache {

    String token;
    String prefix;
    String listeningChannel;
    List<String> admins;
    String verifiedRole;

    public ConfigurationCache(String token, String prefix, List<String> admins, String verifiedRole) {
        this.token = token;
        this.prefix = prefix;
        this.admins = admins;
        this.verifiedRole = verifiedRole;
        getToken();
    }

    /**
     * @return Discord bot token.
     * */
    public String getToken() {
        return token;
    }

    /**
     * @return Command Prefix
     * */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @return List of Discord IDs which represent the administrators.
     * */
    public List<String> getAdmins() {
        return admins;
    }

    /**
     * @return The Role ID which represents the Verified Role.
     * */
    public String getVerifiedRole() {
        return verifiedRole;
    }

    /**
     * @return The Channel ID which the bot will listen to email addresses to.
     */
    public String getListeningChannel() {
        return listeningChannel;
    }
}
