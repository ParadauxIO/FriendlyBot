/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.models.database.TagEntry :  06/02/2021, 17:26
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

package io.paradaux.friendlybot.utils.models.database;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.Date;

public class TagEntry implements Serializable {

    @BsonProperty(value = "guild_id")
    private String guild;

    @BsonProperty(value = "discord_id")
    private String discordId;

    @BsonProperty(value = "content")
    private String content;

    @BsonProperty(value = "time_created")
    private Date timeCreated;

    public TagEntry() {

    }

    public TagEntry(String guild, String discordId, String content, Date timeCreated) {
        this.guild = guild;
        this.discordId = discordId;
        this.content = content;
        this.timeCreated = timeCreated;
    }

    public String getGuild() {
        return guild;
    }

    public String getDiscordId() {
        return discordId;
    }

    public String getContent() {
        return content;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setGuild(String guild) {
        this.guild = guild;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }


}
