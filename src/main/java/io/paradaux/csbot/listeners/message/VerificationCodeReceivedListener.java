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
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class VerificationCodeReceivedListener extends ListenerAdapter {

    private static final ConfigurationEntry configurationEntry = ConfigurationController.getConfigurationEntry();
    DatabaseController databaseController = DatabaseController.INSTANCE;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getChannelType() == ChannelType.PRIVATE) return;

        String discordID = event.getAuthor().getId(), guildID = event.getGuild().getId();
        String verificationCode = message.getContentRaw();

        if (event.getAuthor().isBot()) return;
        if (!event.getChannel().getId().equals(configurationEntry.getVerificationChannelID())) return;

        // If they aren't expected to input a verification code
        if (!databaseController.isPendingVerification(event.getAuthor().getId())) return;

        // If the message isn't a six-digit number
        if (!message.getContentRaw().matches("^[0-9]{1,6}")) return;

        // We don't want the message
        message.delete().queue();

        if (verificationCode.equals(databaseController.getVerificationCode(discordID))) {
            databaseController.setVerifiedUser(discordID, guildID);

            Role verificationRole = message.getGuild().getRoleById(configurationEntry.getVerifiedRoleID());
            message.getGuild().addRoleToMember(message.getMember(), verificationRole).queue();

            event.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage("You have successfully verified your friendly corner discord account.").queue());
            return;
        }

        event.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage("You have entered an invalid verification code. Please check your email and try again." +
                "\nPlease message the bot if you run into issues with this, a moderator/technician will be with you shortly.").queue());
    }

}
