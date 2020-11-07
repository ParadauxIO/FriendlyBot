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

package io.paradaux.csbot.embeds;

import io.paradaux.csbot.EmbedColour;
import io.paradaux.csbot.IEmbedMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import javax.annotation.Nullable;

public class ChatFilterKickEmbed implements IEmbedMessage {

    EmbedBuilder builder = new EmbedBuilder();
    String discordID, message;
    User user;

    public ChatFilterKickEmbed(User user, @Nullable String message) {
        this.discordID = user.getId();
        this.message = message;
        this.user = user;
        create();
    }

    @Override
    public void create() {
        if (message == null) {
            builder.setAuthor(user.getAsTag() + " has triggered the chat filter", null, user.getAvatarUrl());
            builder.setColor(EmbedColour.AUTOMATIC.getColour());
            builder.setDescription("The offending chat message has been removed, and this put in its place. The User has been kicked for this action.");
            return;
        }

        builder.setAuthor("You have triggered the chat filter", null, user.getAvatarUrl());
        builder.setColor(EmbedColour.MODERATION.getColour());
        builder.setDescription("The offending chat message has been removed, and this put in its place. You have been kicked for this action.\n" +
                "Contact the moderators via moderation-csfc@paradaux.io to appeal this, or use the mod-mail feature once you rejoin.");
        builder.addField("Removed Message: ", message, false);
    }

    @Override
    public MessageEmbed build() {
        return builder.build();
    }
}
