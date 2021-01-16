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
import io.paradaux.csbot.managers.SMTPManager;
import io.paradaux.csbot.managers.VerificationManager;
import io.paradaux.csbot.models.exceptions.VerificationException;
import io.paradaux.csbot.models.interal.ConfigurationEntry;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class VerificationEmailReceivedListener extends ListenerAdapter {

    private final ConfigurationEntry config;
    private final Logger logger;

    public VerificationEmailReceivedListener(ConfigurationEntry config, Logger logger) {
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

        // We need to handle the message deletion.
        message.delete().queue();

        String email = message.getContentRaw();

        // If the message is a verification code.
        if (email.matches("^[0-9]{1,6}")) {
            return;
        }

        String discordID = event.getAuthor().getId();
        String guildID = event.getGuild().getId();

        MongoManager mongo = MongoManager.getInstance();

        if (mongo.isVerified(discordID)) {
            event.getAuthor().openPrivateChannel().queue((channel) -> channel
                    .sendMessage("Error: You are already verified.").queue());
            return;
        }

        if (mongo.isPendingVerification(discordID)) {
            event.getAuthor().openPrivateChannel().queue((channel) -> channel
                    .sendMessage("Error: You are already pending verification.").queue());
            return;
        }

        if (!SMTPManager.isValidEmail(email)) {
            event.getAuthor().openPrivateChannel().queue((channel) -> channel
                    .sendMessage("Your message did contain a valid email address.").queue());
            return;
        }

        // If it isn't an @tcd.ie email

        String emailDomain = SMTPManager.getEmailDomain(email);

        if (emailDomain == null) {
            event.getAuthor().openPrivateChannel().queue((channel) -> channel
                    .sendMessage("An unknown error occurred: Email Address was Null").queue());
            return;
        }

        if (!emailDomain.equals("tcd.ie")) {
            event.getAuthor().openPrivateChannel().queue((channel) -> channel
                    .sendMessage("You must use a valid @tcd.ie email address to go through "
                            + "automatic verification.\nIf you are not a trinity student please "
                            + "respond to the bot in this channel and a moderator will be with you"
                            + " shortly.\nPlease message the bot if you run into issues with this,"
                            + " a moderator/technician will be with you shortly.").queue());
            return;
        }

        VerificationManager verification = VerificationManager.getInstance();

        try {
            verification.setPendingVerification(email, guildID, discordID);
        } catch (VerificationException exception) {
            event.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage("An "
                    + "unknown error occurred during verification. Please contact staff. \nError "
                    + "Details: " + exception.getMessage()).queue());
        }

        // Notify the user that there's an email waiting for them.
        event.getAuthor().openPrivateChannel().queue((channel) -> channel
                .sendMessage("Please check your email for a verification token. Once you have"
                        + " it, please paste it back into #verification.\nPlease message the bot"
                        + " if you run into issues with this, a moderator/technician will be with"
                        + " you shortly.").queue());
    }
}
