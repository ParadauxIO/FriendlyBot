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

package io.paradaux.friendlybot.utils.models.automatic;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Date;

public class VerificationEntry {

    @BsonProperty(value = "discord_id")
    String discordID;

    @BsonProperty(value = "guild_id")
    String guildID;

    @BsonProperty(value = "date_verified")
    Date dateVerified;

    public VerificationEntry() {

    }

    public VerificationEntry(String discordID, String guildID, Date dateVerified) {
        this.discordID = discordID;
        this.guildID = guildID;
        this.dateVerified = dateVerified;
    }

    public String getDiscordID() {
        return discordID;
    }

    public VerificationEntry setDiscordID(String discordID) {
        this.discordID = discordID;
        return this;
    }

    public String getGuildID() {
        return guildID;
    }

    public VerificationEntry setGuildID(String guildID) {
        this.guildID = guildID;
        return this;
    }

    public Date getDateVerified() {
        return dateVerified;
    }

    public VerificationEntry setDateVerified(Date dateVerified) {
        this.dateVerified = dateVerified;
        return this;
    }
}
