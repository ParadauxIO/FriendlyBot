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

package io.paradaux.friendlybot.core.utils.models.database;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.Date;

public class TagEntry implements Serializable {

    protected static final long serialVersionUID = 2L;

    @BsonProperty(value = "guild_id")
    private String guildId;

    @BsonProperty(value = "discord_id")
    private String discordId;

    @BsonProperty(value = "id")
    private String id;

    @BsonProperty(value = "content")
    private String content;

    @BsonProperty(value = "time_created")
    private Date timeCreated;

    @BsonProperty(value = "dot_command")
    private boolean isDotCommand;

    public TagEntry() {

    }

    public TagEntry(String guildId, String discordId, String id, String content, Date timeCreated, boolean isDotCommand) {
        this.guildId = guildId;
        this.discordId = discordId;
        this.id = id;
        this.content = content;
        this.timeCreated = timeCreated;
        this.isDotCommand = isDotCommand;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getDiscordId() {
        return discordId;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public boolean isDotCommand() {
        return isDotCommand;
    }

    public TagEntry setGuildId(String guildId) {
        this.guildId = guildId;
        return this;
    }

    public TagEntry setDiscordId(String discordId) {
        this.discordId = discordId;
        return this;
    }

    public TagEntry setId(String id) {
        this.id = id;
        return this;
    }

    public TagEntry setContent(String content) {
        this.content = content;
        return this;
    }

    public TagEntry setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
        return this;
    }

    public void setDotCommand(boolean dotCommand) {
        isDotCommand = dotCommand;
    }
}
