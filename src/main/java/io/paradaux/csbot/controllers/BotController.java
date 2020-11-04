/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.csbot.controllers;

import com.jagrosh.jdautilities.command.CommandClient;
import io.paradaux.csbot.api.ConfigurationCache;
import io.paradaux.csbot.api.SMTPConnection;
import io.paradaux.csbot.listeners.ReadyListener;
import io.paradaux.csbot.listeners.message.DMListener;
import io.paradaux.csbot.listeners.message.VerificationEmailReceivedListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;

import javax.security.auth.login.LoginException;

public class BotController implements  IController {

    public static BotController INSTANCE;

    private static JDA client;
    public static JDA getClient() { return client; }

    private static final ConfigurationCache configurationCache = ConfigurationController.getConfigurationCache();
    private static final Logger logger = LogController.getLogger();

    public void initialise() {
        logger.info("Attempting to login");
        try {
            client = login(configurationCache.getToken());
        } catch (LoginException e) {
            logger.error("Failed to login", e);
            return;
        }
        logger.info("Login successful.");

        INSTANCE = this;
    }

    /**
     * Creates an Instance of JDA from the provided token.
     * @param token The Discord Token taken from the configuration file.
     * @return JDA An Instance of JDA
     * @throws LoginException When logging in proved unsuccessful.
     * */
    public static JDA login (String token) throws LoginException {

        CommandClient commandClient = CommandController.getCommandClient();
        SMTPConnection smtpConnection = EmailController.getSmtpConnection();

        JDABuilder builder = JDABuilder.createDefault(token)
                .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                .setBulkDeleteSplittingEnabled(false)
                .addEventListeners (
                        commandClient,
                        new ReadyListener(configurationCache),
                        new VerificationEmailReceivedListener(configurationCache, smtpConnection),
                        new DMListener(configurationCache)
                );

        if (token == null) {
            throw new LoginException("The Configuration File does not contain a token.");
        }

        return builder.build();
    }

}
