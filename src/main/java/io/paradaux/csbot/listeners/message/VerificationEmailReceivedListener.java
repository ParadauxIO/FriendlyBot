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

import io.paradaux.csbot.models.interal.ConfigurationEntry;
import io.paradaux.csbot.controllers.ConfigurationController;
import io.paradaux.csbot.controllers.DatabaseController;
import io.paradaux.csbot.controllers.EmailController;
import io.paradaux.csbot.controllers.LogController;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.mail.MessagingException;

public class VerificationEmailReceivedListener extends ListenerAdapter {

    private static final ConfigurationEntry configurationEntry = ConfigurationController
            .getConfigurationEntry();
    final DatabaseController databaseController = DatabaseController.INSTANCE;
    final Logger logger = LogController.getLogger();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getChannelType() == ChannelType.PRIVATE) {
            return;
        }

        if (event.getAuthor().isBot()) {
            return;
        }

        if (!event.getChannel().getId().equals(configurationEntry.getVerificationChannelID())) {
            return;
        }

        String discordID = event.getAuthor().getId();

        if (databaseController.isVerified(discordID)) {
            return;
        }

        if (databaseController.isPendingVerification(discordID)) {
            return;
        }


        // We need to handle the message deletion.
        message.delete().queue();


        String email = message.getContentRaw();

        if (!EmailController.isValidEmail(email)) {
            event.getAuthor().openPrivateChannel().queue((channel) -> channel
                    .sendMessage("Your message did contain a valid email address.").queue());
            return;
        }

        // If it isn't an @tcd.ie email
        if (!EmailController.getEmailDomain(email).equals("tcd.ie")) {
            event.getAuthor().openPrivateChannel().queue((channel) -> channel
                    .sendMessage("You must use a valid @tcd.ie email address to go through "
                            + "automatic verification.\nIf you are not a trinity student please "
                            + "respond to the bot in this channel and a moderator will be with you"
                            + " shortly.\nPlease message the bot if you run into issues with this,"
                            + " a moderator/technician will be with you shortly.").queue());

        }

        // Send a verification code to the user
        String verificationCode = EmailController.generateVerificationCode();
        try {
            EmailController.INSTANCE.sendVerificationEmail(email, verificationCode, event
                    .getAuthor().getAsTag());
        } catch (MessagingException exception) {
            logger.error("Error sending email to {} with email address {}", event.getAuthor()
                    .getAsTag(), email, exception);
            return;
        }

        String guildID = event.getGuild().getId();

        // Add the user pending verification to the database.
        databaseController.addPendingVerificationUser(discordID, guildID, verificationCode);

        // Notify the user that there's an email waiting for them.
        event.getAuthor().openPrivateChannel().queue((channel) -> channel
                .sendMessage("Please check your email for a verification token. Once you have"
                        + " it, please paste it back into #verification.\nPlease message the bot"
                        + " if you run into issues with this, a moderator/technician will be with"
                        + " you shortly.").queue());
    }
}
