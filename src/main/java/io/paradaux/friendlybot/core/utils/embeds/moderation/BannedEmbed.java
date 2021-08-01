/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.embeds.moderation.BannedEmbed :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.core.utils.embeds.moderation;

import io.paradaux.friendlybot.core.utils.models.enums.EmbedColour;
import io.paradaux.friendlybot.core.utils.models.interfaces.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

public class BannedEmbed implements Embed {

    final EmbedBuilder builder = new EmbedBuilder();

    public BannedEmbed(String reason, String incidentID) {
        builder.setColor(EmbedColour.ISSUE.getColour());
        builder.setAuthor("The Computer Science Friendly Discord | Banned", null,
                "https://cdn.discordapp.com/icons/757903425311604786/ac7d6af0fc3cf43e3709257d7d25c06f.png");
        builder.setDescription("You have been banned from The Computer Science Friendly Discord. "
                + "The Specified Reason was `" + reason + "`\n" + "You may appeal this ban by"
                + " contacting your class representative.");
        builder.setFooter("User Banned: Incident ID " + incidentID + " | CS Friendly Bot");
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
