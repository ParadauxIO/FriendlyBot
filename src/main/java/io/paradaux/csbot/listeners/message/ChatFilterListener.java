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

import io.paradaux.csbot.ConfigurationCache;
import io.paradaux.csbot.controllers.*;
import io.paradaux.csbot.embeds.ChatFilterKickEmbed;
import io.paradaux.csbot.embeds.ChatFilterWarnEmbed;
import io.paradaux.csbot.models.ChatFilterEntry;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class ChatFilterListener extends ListenerAdapter {

    // Dependencies
    private static final ConfigurationCache configurationCache = ConfigurationController.getConfigurationCache();
    private static final Logger logger = LogController.getLogger();

    private String[] warnable, kickable;
    private ChatFilterEntry chatFilterEntry;

    public ChatFilterListener() {
        try {
            chatFilterEntry = FileController.INSTANCE.readChatFilter();
        } catch (FileNotFoundException exception) {
            logger.error("Failed to create ChatFilter Listener", exception);
            return;
        }

        this.warnable = chatFilterEntry.getWarnable();
        this.kickable = chatFilterEntry.getKickable();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getAuthor().isBot()) return;

        String messageContent = message.getContentRaw();
        String guildID = message.getGuild().getId();
        String discordID = message.getAuthor().getId();

        if (containsWarnableWord(messageContent)) {
            new ChatFilterWarnEmbed(message.getAuthor(), null).sendEmbed(event.getChannel(), null);

            event.getAuthor().openPrivateChannel().queue((channel) -> {
                new ChatFilterWarnEmbed(message.getAuthor(), messageContent).sendEmbed(channel, null);
            });

            ModerationActionController.INSTANCE.warnUser(guildID, discordID, "Illicit word use", messageContent, true);

            message.delete().queue();

            AuditLogController.INSTANCE.log("Warn Chat Filter Trigger\n**Message**: " + messageContent, message.getAuthor().getAsTag(), "Removed Message | Warned User" );
        }

        if (containsKickableWord(messageContent)) {
            new ChatFilterKickEmbed(message.getAuthor(), null).sendEmbed(event.getChannel(), null);

            event.getAuthor().openPrivateChannel().queue((channel) -> {
                new ChatFilterKickEmbed(message.getAuthor(), messageContent).sendEmbed(channel, null);
            });

            ModerationActionController.INSTANCE.kickUser(guildID, discordID, "Illicit word use", messageContent);

            message.delete().queue();

            AuditLogController.INSTANCE.log("Kick Chat Filter Trigger\n**Message**: " + messageContent, message.getAuthor().getAsTag(), "Removed Message | Kicked User");

        }

    }

    public boolean containsWarnableWord(String input) {
        return Arrays.stream(warnable).anyMatch(input::contains);
    }
    public boolean containsKickableWord(String input) {
        return Arrays.stream(kickable).anyMatch(input::contains);
    }

}
