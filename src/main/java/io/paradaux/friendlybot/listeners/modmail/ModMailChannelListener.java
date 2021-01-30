/*
 * Copyright (c) 2021 |  Rían Errity. GPLv3
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

package io.paradaux.friendlybot.listeners.modmail;

import io.paradaux.friendlybot.managers.DiscordBotManager;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.utils.embeds.modmail.ModMailReceivedEmbed;
import io.paradaux.friendlybot.utils.embeds.modmail.ModMailSentEmbed;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.database.ModMailEntry;
import io.paradaux.friendlybot.utils.models.interfaces.Embed;
import io.paradaux.friendlybot.utils.models.types.DiscordEventListener;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Date;

public class ModMailChannelListener extends DiscordEventListener {

    private final MongoManager mongo;

    public ModMailChannelListener(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.mongo = MongoManager.getInstance();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getChannelType() == ChannelType.PRIVATE) {
            return;
        }

        if (!message.getChannel().getId().equals(getConfig().getModmailInputChannelID())) {
            return;
        }

        message.delete().queue();

        String userId = event.getAuthor().getId();
        String userTag = event.getAuthor().getAsTag();
        String issue = message.getContentRaw();
        String ticketNumber = mongo.getNextTicketNumber();
        String incidentID = mongo.getNextIncidentID();

        ModMailEntry entry = new ModMailEntry()
                .setIssue(issue)
                .setModmailMethod("channel")
                .setStatus(ModMailEntry.ModMailStatus.OPEN)
                .setTicketNumber(ticketNumber)
                .setTimeOpened(new Date())
                .setLastResponded(new Date())
                .setUserID(userId)
                .setUserTag(userTag);

        Embed receivedEmbed = new ModMailReceivedEmbed(event.getMessage().getAuthor(),
                issue, ticketNumber, incidentID);
        ModMailSentEmbed sentEmbed = new ModMailSentEmbed(ticketNumber, issue);

        receivedEmbed.sendEmbed(DiscordBotManager.getInstance().getChannel(getConfig().getModmailOutputChannelID()));
        event.getAuthor().openPrivateChannel().queue((privateChannel) -> privateChannel
                .sendMessage(sentEmbed.getEmbed()).queue());

        mongo.addModMailEntry(entry);

        // TODO tidy
    }


}
