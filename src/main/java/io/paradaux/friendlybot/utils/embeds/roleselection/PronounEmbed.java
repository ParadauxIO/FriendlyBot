/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.embeds.roleselection.PronounEmbed :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.utils.embeds.roleselection;

import io.paradaux.friendlybot.utils.models.interfaces.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class PronounEmbed implements Embed {

    final EmbedBuilder builder = new EmbedBuilder();

    @SuppressWarnings("checkstyle:AvoidEscapedUnicodeCharacters")
    public PronounEmbed() {
        builder.setDescription("**What pronouns would you like people to use for you?**\n\nIf you"
                + " selected other, please feel free to use your nickname or your status in "
                + "order to provide additional information.");

        builder.addField("He/Him", "\uD83C\uDDE6", true);
        builder.addField("She/Her", "\uD83C\uDDE7", true);
        builder.addField("They/Them", "\uD83C\uDDE8", true);
        builder.addField("Other", "\uD83C\uDDE9", true);
    }

    @Override
    public void sendEmbed(TextChannel channel) {
        channel.sendMessage(builder.build()).queue();
    }
    public EmbedBuilder getBuilder() { return builder; }

}
