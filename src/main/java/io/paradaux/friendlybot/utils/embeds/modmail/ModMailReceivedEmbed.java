/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.embeds.modmail.ModMailReceivedEmbed :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.utils.embeds.modmail;

import io.paradaux.friendlybot.utils.models.enums.EmbedColour;
import io.paradaux.friendlybot.utils.models.interfaces.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Date;

public class ModMailReceivedEmbed implements Embed {

    final EmbedBuilder builder = new EmbedBuilder();

    public ModMailReceivedEmbed(User user, String messageContent, String ticketID,
                                String incidentID) {

        builder.setColor(EmbedColour.AUTOMATIC.getColour());
        builder.setDescription("New ModMail Message.");
        builder.addField("User: ", user.getAsTag(), true);
        builder.addField("UserID: ", user.getId(), true);

        builder.addBlankField(true);

        builder.addField("Ticket Number: ", ticketID, true);
        builder.addField("Incident ID: ", incidentID, true);

        builder.addBlankField(true);

        builder.addField("Message Content: ", messageContent, true);
        builder.setFooter("Use ;respond <ticketNumber> <message> to respond. | Use ;close "
                + "<ticketNumber> to close the ticket, This will delete the message in the mod-"
                + "mail-output channel but retain the record in the database "
                + "for future reference.");
        builder.setTimestamp(new Date().toInstant());
    }



    @Override
    public void sendEmbed(TextChannel channel) {
        channel.sendMessage(builder.build()).queue();
    }

    public MessageEmbed getEmbed() {
        return builder.build();
    }
    public EmbedBuilder getBuilder() { return builder; }
}
