/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.embeds.AuditLogEmbed :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.utils.embeds;

import io.paradaux.friendlybot.utils.models.enums.EmbedColour;
import io.paradaux.friendlybot.utils.models.interfaces.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Date;

public class AuditLogEmbed implements Embed {

    final EmbedBuilder builder = new EmbedBuilder();

    public enum Action {
        WARN, KICK, BAN, TEMP_BAN
    }

    public AuditLogEmbed(Action action, User user, String reason, String incidentID) {
        builder.setColor(EmbedColour.MODERATION.getColour());
        builder.addField("Action: ", action.toString(), true);
        builder.addField("Incident ID: ", incidentID, true);
        builder.addBlankField(true);
        builder.addField("User: ",user.getAsTag(), true);
        builder.addField("User ID: ", user.getId(), true);
        builder.addBlankField(true);
        builder.addField("Message: ", reason, false);
        builder.setTimestamp(new Date().toInstant());
    }

    public AuditLogEmbed(Action action, User user, User staffMember, String reason,
                         String incidentID) {
        builder.setColor(EmbedColour.MODERATION.getColour());
        builder.addField("Action: ", action.toString(), true);
        builder.addField("Incident ID: ", incidentID, true);
        builder.addBlankField(true);
        builder.addField("User: ", user.getAsTag(), true);
        builder.addField("User ID: ", user.getId(),     true);
        builder.addBlankField(true);
        builder.addField("Staff: ", staffMember.getAsTag(), true);
        builder.addField("Staff ID: ", staffMember.getId(), true);
        builder.addBlankField(true);
        builder.addField("Message: ", reason, false);
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
