/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.embeds.moderation.ChatFilterTriggeredEmbed :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.utils.embeds.moderation;

import io.paradaux.friendlybot.utils.embeds.AuditLogEmbed;
import io.paradaux.friendlybot.utils.models.enums.EmbedColour;
import io.paradaux.friendlybot.utils.models.interfaces.Embed;
import io.paradaux.friendlybot.utils.models.types.ModerationAction;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Date;

public class ChatFilterTriggeredEmbed implements Embed {

    final EmbedBuilder builder = new EmbedBuilder();

    public ChatFilterTriggeredEmbed(ModerationAction action, String messageContent,
                                    String detectedWord, String incidentID) {
        builder.setColor(EmbedColour.AUTOMATIC.getColour());
        builder.setDescription("You have triggered the chat filter. Please avoid using sensitive "
                + "words, you can view the filter [here](https://github.com/ParadauxIO"
                + "/ComputerScienceFriendlyBot/wiki/Chat-Filter).");

        builder.addField("Action Taken: ", action.toString(), true);
        builder.addField("Incident ID: ", incidentID, true);
        builder.addField("Detected Word: ", detectedWord, true);

        builder.addField("Message Content: ", messageContent, true);
        builder.setFooter("It's very possible this detection was a mistake, if it was please "
                + "explain how it was a mistake, and include the incident ID included above so we "
                + "can remove this from your record.");
        builder.setTimestamp(new Date().toInstant());
    }

    @Override
    public void sendEmbed(TextChannel channel) {
        channel.sendMessage(builder.build()).queue();
    }
    public EmbedBuilder getBuilder() { return builder; }
}
