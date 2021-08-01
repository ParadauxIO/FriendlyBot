/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.models.database.KickEntry :  31/01/2021, 01:26
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

public class KickEntry implements Serializable {

    protected static final long serialVersionUID = 1L;

    @BsonProperty(value="guild_id")
    private String guildId;

    @BsonProperty(value = "incident_id")
    private String incidentID;

    @BsonProperty(value = "target_tag")
    private String userTag;

    @BsonProperty(value = "target_id")
    private String userID;

    @BsonProperty(value = "staff_tag")
    private String staffTag;

    @BsonProperty(value = "staff_id")
    private String staffID;

    @BsonProperty(value = "reason")
    private String reason;

    @BsonProperty(value = "timestamp")
    private Date timestamp;

    public KickEntry() {

    }

    public KickEntry(String guildId, String incidentID, String userTag, String userID, String staffTag, String staffID, String reason,
                     Date timestamp) {
        this.guildId = guildId;
        this.incidentID = incidentID;
        this.userTag = userTag;
        this.userID = userID;
        this.staffTag = staffTag;
        this.staffID = staffID;
        this.reason = reason;
        this.timestamp = timestamp;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getIncidentID() {
        return incidentID;
    }

    public String getUserTag() {
        return userTag;
    }

    public String getUserID() {
        return userID;
    }

    public String getStaffTag() {
        return staffTag;
    }

    public String getStaffID() {
        return staffID;
    }

    public String getReason() {
        return reason;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public KickEntry setIncidentID(String incidentID) {
        this.incidentID = incidentID;
        return this;
    }

    public KickEntry setUserTag(String userTag) {
        this.userTag = userTag;
        return this;
    }

    public KickEntry setUserID(String userID) {
        this.userID = userID;
        return this;
    }

    public KickEntry setStaffTag(String staffTag) {
        this.staffTag = staffTag;
        return this;
    }

    public KickEntry setStaffID(String staffID) {
        this.staffID = staffID;
        return this;
    }

    public KickEntry setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public KickEntry setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public KickEntry setGuildId(String guildId) {
        this.guildId = guildId;
        return this;
    }
}
