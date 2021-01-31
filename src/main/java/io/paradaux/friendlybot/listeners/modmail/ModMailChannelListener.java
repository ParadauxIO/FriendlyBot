/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.listeners.modmail.ModMailChannelListener :  31/01/2021, 01:26
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
