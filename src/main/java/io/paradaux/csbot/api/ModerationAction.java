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

package io.paradaux.csbot.api;

import io.paradaux.csbot.embeds.EmbedColour;
import io.paradaux.csbot.embeds.ModerationActionEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed;

import javax.annotation.Nullable;

public class ModerationAction {

    public static MessageEmbed kickUser(String guildID, String discordID, String reason, @Nullable String context) {
        return new ModerationActionEmbed(EmbedColour.MODERATION, discordID, guildID, reason).build();
    }

    public static MessageEmbed warnUser(String guildID, String discordID, String reason, @Nullable String context) {
        return new ModerationActionEmbed(EmbedColour.MODERATION, discordID, guildID, reason).build();
    }

    public static MessageEmbed banUser(String guildID, String discordID, String reason) {
        return new ModerationActionEmbed(EmbedColour.MODERATION, discordID, guildID, reason).build();
    }
}
