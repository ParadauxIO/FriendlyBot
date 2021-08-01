/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.models.database.UserSettingsEntry :  03/02/2021, 01:21
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

public class UserSettingsEntry implements Serializable {

    protected static final long serialVersionUID = 1L;

    @BsonProperty(value = "guild_id")
    private String guildId;

    @BsonProperty(value = "discord_id")
    private String discordId;

    @BsonProperty(value = "discord_tag")
    private String firstSavedDiscordTag;

    @BsonProperty(value = "do_train_ai")
    private boolean doTrainAi;

    @BsonProperty(value = "last_changed_color")
    private Date lastSetColor;

    @BsonProperty(value = "custom_color_role")
    private String customColorRole;

    public UserSettingsEntry() {
        
    }

    public UserSettingsEntry(String guildId, String discordId, String firstSavedDiscordTag, boolean doTrainAi, Date lastSetColor,
                             String customColorRole) {
        this.guildId = guildId;
        this.discordId = discordId;
        this.firstSavedDiscordTag = firstSavedDiscordTag;
        this.doTrainAi = doTrainAi;
        this.lastSetColor = lastSetColor;
        this.customColorRole = customColorRole;
    }

    public UserSettingsEntry setGuildId(String guildId) {
        this.guildId = guildId;
        return this;
    }

    public UserSettingsEntry setDiscordId(String discordId) {
        this.discordId = discordId;
        return this;
    }

    public UserSettingsEntry setFirstSavedDiscordTag(String firstSavedDiscordTag) {
        this.firstSavedDiscordTag = firstSavedDiscordTag;
        return this;
    }

    public UserSettingsEntry setDoTrainAi(boolean doTrainAi) {
        this.doTrainAi = doTrainAi;
        return this;
    }

    public UserSettingsEntry setLastSetColor(Date lastSetColor) {
        this.lastSetColor = lastSetColor;
        return this;
    }

    public UserSettingsEntry setCustomColorRole(String customColorRole) {
        this.customColorRole = customColorRole;
        return this;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getDiscordId() {
        return discordId;
    }

    public String getFirstSavedDiscordTag() {
        return firstSavedDiscordTag;
    }

    public boolean isDoTrainAi() {
        return doTrainAi;
    }

    public Date getLastSetColor() {
        return lastSetColor;
    }

    public String getCustomColorRole() {
        return customColorRole;
    }
}
