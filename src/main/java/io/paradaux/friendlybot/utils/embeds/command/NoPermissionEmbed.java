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

package io.paradaux.friendlybot.embeds.command;

import io.paradaux.friendlybot.EmbedColour;
import io.paradaux.friendlybot.models.interfaces.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class NoPermissionEmbed implements Embed {

    final EmbedBuilder builder = new EmbedBuilder();

    public NoPermissionEmbed(User author, String command, String requiredRoles) {
        builder.setColor(EmbedColour.ISSUE.getColour());
        builder.setAuthor(author.getAsTag(), null, author.getAvatarUrl());
        builder.setDescription("You do not have permission to run "
                + "`" + command + "`" + "\nThis is restricted to: `" + requiredRoles + "`");

        builder.setFooter("No Permission Error | CS Friendly Bot");
    }

    @Override
    public void sendEmbed(TextChannel channel) {
        channel.sendMessage(builder.build()).queue();
    }
}