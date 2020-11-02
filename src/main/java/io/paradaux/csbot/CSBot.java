

/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.csbot;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import io.paradaux.csbot.api.ConfigurationCache;
import io.paradaux.csbot.api.ConfigurationUtils;
import io.paradaux.csbot.api.Logging;
import io.paradaux.csbot.commands.InviteCommand;
import io.paradaux.csbot.listeners.MessageReceivedListener;
import io.paradaux.csbot.listeners.ReadyListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;

import javax.security.auth.login.LoginException;
import java.io.FileNotFoundException;

/**
 * CSBot is the main (executable) class for the project. It provides a set of lazy dependency injection instances
 *
 * @author Rían Errity
 * @version Last Modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 * */

public class CSBot {

    private static JDA client;
    public static JDA getClient() { return client; }

    private static ConfigurationCache configurationCache;
    public static ConfigurationCache getConfigurationCache() { return configurationCache; }

    /**
     * This is the main method for the application. It handles the instantiation of dependencies, and the discord bot as a whole
     * @param args Command line arguments are not used in this application.
     * */
    public static void main(String[] args) {

        // Instantiate Logger instance.
        Logging logging = new Logging();
        Logger logger = Logging.getLogger();


        // Version Information
        logger.info("CSBot v0.1.0 - Maintained by Rían Errity <rian@paradaux.io>");

        // Prepare Configuration File/Cache
        logger.info("Preparing Configuration File...");
        ConfigurationUtils.deployConfiguration();
        try {
            configurationCache = ConfigurationUtils.readConfigurationFile();
        } catch (FileNotFoundException exception) {
            logger.error("Could not find the configuration file!", exception);
        }

        // Login
        logger.info("Attempting to login...");
        try {
            client = login(configurationCache.getToken());
        } catch (LoginException exception) {
            logger.error("There was an issue logging in: ", exception);
            return;
        }
    }

    /**
     * Creates an Instance of JDA from the provided token.
     * @param token The Discord Token taken from the configuration file.
     * @return JDA An Instance of JDA
     * @throws LoginException When logging in proved unsuccessful.
     * */
    public static JDA login (String token) throws LoginException {
        Logger logger = Logging.getLogger();

        JDABuilder builder = JDABuilder.createDefault(token)
                .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .setBulkDeleteSplittingEnabled(false)
                .addEventListeners(new ReadyListener(configurationCache), new MessageReceivedListener(configurationCache), createCommandClient());

        if (token == null) {
            throw new LoginException("The Configuration File does not contain a token.");
        }

        return builder.build();
    }

    /**
     * Creates a CommanddClient instance which is provided by JDAUtilities. It handles a lot of the command listening, and acts as a clean command wrapper.
     * @return An instance of CommandClient.
     * */
    public static CommandClient createCommandClient() {
        CommandClientBuilder builder = new CommandClientBuilder()
                .setOwnerId(configurationCache.getAdmins().get(0))
                .setPrefix(configurationCache.getPrefix())
                .setActivity(Activity.playing("with your emotions"))
                .addCommand(new InviteCommand());

        return builder.build();
    }

}
