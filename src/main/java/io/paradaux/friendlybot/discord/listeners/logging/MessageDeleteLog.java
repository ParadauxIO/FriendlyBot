/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.listeners.logging.MessageDeleteLog :  03/02/2021, 01:34
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.paradaux.friendlybot.discord.listeners.logging;

import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.database.GuildSettingsEntry;
import io.paradaux.friendlybot.core.utils.models.database.MessageEntry;
import io.paradaux.friendlybot.core.utils.models.types.DiscordEventListener;
import io.paradaux.friendlybot.managers.DiscordBotManager;
import io.paradaux.friendlybot.managers.GuildSettingsManager;
import io.paradaux.friendlybot.managers.MongoManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class MessageDeleteLog extends DiscordEventListener {

    private final MongoManager mongo;
    private final GuildSettingsManager guilds;

    public MessageDeleteLog(ConfigurationEntry config, Logger logger, MongoManager mongo, GuildSettingsManager guilds) {
        super(config, logger);
        this.mongo = mongo;
        this.guilds = guilds;
    }

    @Override
    public void onGuildMessageDelete(@NotNull GuildMessageDeleteEvent event) {

        GuildSettingsEntry guild = guilds.getGuild(event.getGuild().getId());

        if (guild.getMessageLogChannel() == null || guild.getMessageLogChannel().isEmpty()) {
            return;
        }

        TextChannel messageLogChannel = DiscordBotManager.getInstance().getChannel(guild.getMessageLogChannel());
        MessageEntry entry = mongo.getLoggedMessage(event.getMessageId());

        if (entry == null) {
            return;
        }

        User user = DiscordBotManager.getInstance().getUser(entry.getAuthorId());

        if (user.isBot()) {
            return;
        }

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(0xFF0000)
                .setAuthor(user.getName(), null, user.getAvatarUrl())
                .setTitle("Deleted in #" + entry.getChannel())
                .setTimestamp(entry.getDate().toInstant());

        if (!(entry.getNewContent() == null)) {
            builder.addField("Original Message", entry.getContent(), false)
                    .addField("Message at time of deletion", entry.getNewContent(), false);
        } else {
            builder.setDescription(entry.getContent());
        }

        messageLogChannel.sendMessage(builder.build()).queue();
    }
}

