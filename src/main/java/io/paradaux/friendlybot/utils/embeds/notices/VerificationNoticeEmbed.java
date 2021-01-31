/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.embeds.notices.VerificationNoticeEmbed :  31/01/2021, 01:27
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

package io.paradaux.friendlybot.utils.embeds.notices;

import io.paradaux.friendlybot.utils.models.interfaces.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class VerificationNoticeEmbed implements Embed {

    final EmbedBuilder builder = new EmbedBuilder();

    public VerificationNoticeEmbed() {
        builder.addField("Information", "The Computer Science Friendly Corner is "
                + "configured so that everyone must verify their @tcd.ie email address to "
                + "gain access.\n\n If you are not a Trinity student you can also gain "
                + "access, but you'll have to contact a moderator by messaging the discord bot or "
                + "by typing in #mod-mail in order to do so.", false);

        builder.addField("Verification Tutorial", ":one: :: Enter your email address in "
                + "#verification (this channel)\n:two: :: Check your email address for your "
                + "verification code\n:three: :: Enter your verification code in "
                + "#verification (this channel)\n\nOnce you've completed those three easy "
                + "steps you'll be a full member of the discord!\n\n**N.B** Unverified"
                + " users are purged on a regular basis, so please go through the verification "
                + "process as soon as possible.\n\nEmails are not stored on our servers, we respect"
                + " your privacy!",false);
    }

    @Override
    public void sendEmbed(TextChannel channel) {
        channel.sendMessage(builder.build()).queue();
    }
    public EmbedBuilder getBuilder() { return builder; }


}
