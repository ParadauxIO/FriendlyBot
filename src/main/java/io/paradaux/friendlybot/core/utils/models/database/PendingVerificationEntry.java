/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.models.database.PendingVerificationEntry :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.core.utils.models.database;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

public class PendingVerificationEntry implements Serializable {

    protected static final long serialVersionUID = 1L;

    @BsonProperty(value = "guild_id")
    private String guildID;

    @BsonProperty(value = "discord_id")
    private String discordID;

    @BsonProperty(value = "verification_id")
    private String verificationCode;

    public PendingVerificationEntry() {

    }

    public PendingVerificationEntry(String guildID, String discordID, String verificationCode) {
        this.guildID = guildID;
        this.discordID = discordID;
        this.verificationCode = verificationCode;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
