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

package io.paradaux.csbot.embeds.roleselection;

import io.paradaux.csbot.interfaces.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class InterestsEmbed implements Embed {

    final EmbedBuilder builder = new EmbedBuilder();

    @SuppressWarnings("checkstyle:AvoidEscapedUnicodeCharacters")
    public InterestsEmbed() {
        builder.setDescription("**What are you interested in?**");
        builder.addField("Anime / Manga", "<:ayaya:758450629764186152>", true);
        builder.addField("Film / TV", "\uD83C\uDF9E", true);
        builder.addField("Cooking", "\uD83D\uDC69\u200D\uD83C\uDF73", true);
        builder.addField("Gaming", "<:steve:758451458042888213>", true);
        builder.addField("Music", "\uD83C\uDFB5", true);
        builder.addField("Sports", "\uD83C\uDFC9", true);
    }

    @Override
    public void sendEmbed(TextChannel channel) {
        channel.sendMessage(builder.build()).queue();
    }

}
