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
import io.paradaux.csbot.controllers.BotController;
import io.paradaux.csbot.controllers.ConfigurationController;
import io.paradaux.csbot.controllers.LogController;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * PrivateMessageReceivedListener listens to the VerificationCodes sent to the bot privately, parses them and sets the user as verified if approrpriate.
 *
 * @author Rían Errity
 * @version Last Modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 * @see io.paradaux.csbot.CSBot
 * */


public class ModMailDMListener extends ListenerAdapter {

    // Dependencies
    private static final ConfigurationEntry configurationEntry = ConfigurationController.getConfigurationEntry();
    private static final Logger logger = LogController.getLogger();

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getAuthor().isBot()) return;

        TextChannel channel = BotController.getClient()
                .getGuildById(configurationEntry.getCsFriendlyGuildID())
                .getTextChannelById(configurationEntry.getModmailOutputChannelID());

//        ModMailEntryEmbed embed = new ModMailEntryEmbed(event.getAuthor().getAsTag(), event.getAuthor().getId(), message.getContentRaw());
//        embed.create();
//
//        embed.sendEmbed(channel, null);

        event.getAuthor().openPrivateChannel().queue((privateChannel) -> privateChannel.sendMessage("**Your message has been sent to the moderators.**" +
                "\nThe Moderation Team will get back to you as soon as possible.").queue());

    }

}
