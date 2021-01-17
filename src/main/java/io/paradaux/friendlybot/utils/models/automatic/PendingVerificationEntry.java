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

package io.paradaux.friendlybot.models.automatic;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class PendingVerificationEntry {

    @BsonProperty(value = "discord_id")
    String discordID;

    @BsonProperty(value = "guild_id")
    String guildID;

    @BsonProperty(value = "verification_id")
    String verificationCode;

    public PendingVerificationEntry() {

    }

    public PendingVerificationEntry(String discordID, String guildID, String verificationCode) {
        this.discordID = discordID;
        this.guildID = guildID;
        this.verificationCode = verificationCode;
    }

    public String getDiscordID() {
        return discordID;
    }

    public PendingVerificationEntry setDiscordID(String discordID) {
        this.discordID = discordID;
        return this;
    }

    public String getGuildID() {
        return guildID;
    }

    public PendingVerificationEntry setGuildID(String guildID) {
        this.guildID = guildID;
        return this;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public PendingVerificationEntry setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
        return this;
    }
}
