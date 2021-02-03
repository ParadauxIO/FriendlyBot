/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.listeners.logging.UpdatedMessageLog :  03/02/2021, 00:53
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

package io.paradaux.friendlybot.listeners.logging;

import io.paradaux.friendlybot.managers.DiscordBotManager;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.database.MessageEntry;
import io.paradaux.friendlybot.utils.models.types.DiscordEventListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class UpdatedMessageLog extends DiscordEventListener {

    private final MongoManager mongo;

    public UpdatedMessageLog(ConfigurationEntry config, Logger logger, MongoManager mongo) {
        super(config, logger);
        this.mongo = mongo;
    }

    @Override
    public void onGuildMessageUpdate(@NotNull GuildMessageUpdateEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        TextChannel messageLogChannel = DiscordBotManager.getInstance().getChannel(getConfig().getMessageLogChannel());

        MessageEntry entry = mongo.getLoggedMessage(event.getMessageId());

        if (entry == null) {
            return;
        }

        entry = entry.setNewContent(event.getMessage().getContentRaw());

        mongo.updateLoggedMessage(entry.getMessageId(), entry);

        User user = DiscordBotManager.getInstance().getUser(entry.getAuthorId());

        EmbedBuilder builder = new EmbedBuilder()
                .setColor(0xda9f20)
                .setAuthor(user.getName(), null, user.getAvatarUrl())
                .setTitle("Modified Message in #" + entry.getChannel())
                .setDescription(entry.getContent())
                .addField("Old", entry.getContent(), false)
                .addField("New", entry.getNewContent(), false)
                .setTimestamp(entry.getDate().toInstant());

        messageLogChannel.sendMessage(builder.build()).queue();
    }
}
