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

package io.paradaux.friendlybot.utils.models.database;

import java.io.Serializable;

public class UserSettingsEntry implements Serializable {

    private String discordId;
    private String firstSavedDiscordTag;

    private boolean saveBotMessages;
    private boolean executeCommands;

    public UserSettingsEntry() {
        
    }

    public UserSettingsEntry(String discordId, String firstSavedDiscordTag, boolean saveBotMessages, boolean executeCommands) {
        this.discordId = discordId;
        this.firstSavedDiscordTag = firstSavedDiscordTag;
        this.saveBotMessages = saveBotMessages;
        this.executeCommands = executeCommands;
    }

    public UserSettingsEntry setDiscordId(String discordId) {
        this.discordId = discordId;
        return this;
    }

    public UserSettingsEntry setFirstSavedDiscordTag(String firstSavedDiscordTag) {
        this.firstSavedDiscordTag = firstSavedDiscordTag;
        return this;
    }

    public UserSettingsEntry setSaveBotMessages(boolean saveBotMessages) {
        this.saveBotMessages = saveBotMessages;
        return this;
    }

    public UserSettingsEntry setExecuteCommands(boolean executeCommands) {
        this.executeCommands = executeCommands;
        return this;
    }

    public String getDiscordId() {
        return discordId;
    }

    public String getFirstSavedDiscordTag() {
        return firstSavedDiscordTag;
    }

    public boolean isSaveBotMessages() {
        return saveBotMessages;
    }

    public boolean isExecuteCommands() {
        return executeCommands;
    }
}
