/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.embeds.AnnouncementEmbed :  31/01/2021, 01:26
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

import io.paradaux.friendlybot.utils.models.interfaces.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class AnnouncementEmbed implements Embed {

    final EmbedBuilder builder = new EmbedBuilder();

    public AnnouncementEmbed() {

        builder.setAuthor("The University Times" , "http://www.universitytimes.ie/2021/01/pandemic-or-not-you-dont-have-to-be-at-rock-bottom-to-seek-help/", "https://cdn.paradaux.io/img/7lai2.jpg");
        builder.setThumbnail("https://cdn.paradaux.io/img/7lai2.jpg");

        builder.setDescription("As i'm sure you are all very aware, we're in the middle of Ireland's third lockdown and, because of this, we would like to take the opportunity to make you aware of a fantastic University Times article which was published a few days ago (click the \"University Times\" link above.) It's titled: \"Pandemic or Not, You Don’t Have to Be at Rock Bottom to Seek Help.\"\n" + "\n" + "It's important, now than possibly ever for some of us, to recognise that being socially isolated can be *really* hard. If you or anyone you know is struggling, please reach out to them. It could mean a lot. \n" + "\n" + "Please do check out the full article [here](http://www.universitytimes.ie/2021/01/pandemic-or-not-you-dont-have-to-be-at-rock-bottom-to-seek-help/)!");
    }

    @Override
    public void sendEmbed(TextChannel channel) {
        channel.sendMessage(builder.build()).queue();
    }
    public EmbedBuilder getBuilder() { return builder; }
}
