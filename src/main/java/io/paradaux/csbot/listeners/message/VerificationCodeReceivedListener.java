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

package io.paradaux.csbot.listeners.message;

import io.paradaux.csbot.managers.MongoManager;
import io.paradaux.csbot.managers.VerificationManager;
import io.paradaux.csbot.models.exceptions.VerificationException;
import io.paradaux.csbot.models.interal.ConfigurationEntry;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class VerificationCodeReceivedListener extends ListenerAdapter {

    private final ConfigurationEntry config;
    private final Logger logger;

    public VerificationCodeReceivedListener(ConfigurationEntry config, Logger logger) {
        this.config = config;
        this.logger = logger;
    }


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getChannelType() == ChannelType.PRIVATE) {
            return;
        }

        if (event.getAuthor().isBot()) {
            return;
        }

        if (!event.getChannel().getId().equals(config.getVerificationChannelID())) {
            return;
        }

        MongoManager mongo = MongoManager.getInstance();

        // If they aren't expected to input a verification code
        if (!mongo.isPendingVerification(event.getAuthor().getId())) {
            return;
        }

        // We don't want the message
        message.delete().queue();

        // If the message isn't a six-digit number
        if (!message.getContentRaw().matches("^[0-9]{1,6}")) {
            return;
        }

        String verificationCode = message.getContentRaw();
        String discordID = event.getAuthor().getId();
        String guildID = event.getGuild().getId();

        VerificationManager verification = VerificationManager.getInstance();

        try {
            if (verification.setVerified(discordID, guildID, verificationCode)) {
                event.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(
                        "You" + " have successfully verified your friendly corner discord account"
                                + ".").queue());
            } else {
                event.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(
                        "The Verification code you provided was incorrect. If this issue "
                                + "persists please contact staff.").queue());
            }
        } catch (VerificationException exception) {
            event.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage("An "
                    + "unknown error occurred during verification. Please contact staff. \nError "
                    + "Details: " + exception.getMessage()).queue());
        }
    }

}
