/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.csbot.embeds.notices;

import io.paradaux.csbot.embeds.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class VerificationNoticeEmbed implements Embed {

    EmbedBuilder builder = new EmbedBuilder();

    public VerificationNoticeEmbed() {
        builder.addField("Information", "The Computer Science Friendly Corner is configured so that everyone must verify their @tcd.ie email address to gain access.\n\n " +
                "If you are not a Trinity student you can also gain access, but you'll have to contact a moderator by messaging the discord bot or by typing in #mod-mail in order to do so.", false);
        builder.addField("Verification Tutorial",
                ":one: :: Enter your email address in #verification (this channel)\n"
                        + ":two: :: Check your email address for your verification code\n"
                        + ":three: :: Enter your verification code in #verification (this channel)\n\n"
                        + "Once you've completed those three easy steps you'll be a full member of the discord!\n\n"
                        + "**N.B** Unverified users are purged on a regular basis, so please go through the verification process as soon as possible.\n\n"
                        + "Emails are not stored on our servers, we respect your privacy!", false);
    }

    @Override
    public void sendEmbed(TextChannel channel) {
        channel.sendMessage(builder.build()).queue();
    }


}
