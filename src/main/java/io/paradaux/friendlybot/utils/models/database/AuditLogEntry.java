/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.models.database.AuditLogEntry :  31/01/2021, 01:26
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

import io.paradaux.friendlybot.utils.embeds.AuditLogEmbed;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Date;

public class AuditLogEntry {

    @BsonProperty(value = "action")
    AuditLogEmbed.Action action;

    @BsonProperty(value = "incident_id")
    String incidentID;

    @BsonProperty(value = "user_tag")
    String userTag;

    @BsonProperty(value = "user_id")
    String userID;

    @BsonProperty(value = "staff_tag")
    String staffTag;

    @BsonProperty(value = "staff_id")
    String staffID;

    @BsonProperty(value = "reason")
    String reason;

    @BsonProperty(value = "timestamp")
    Date timestamp;

    public AuditLogEntry() {

    }

    public AuditLogEntry(AuditLogEmbed.Action action, String incidentID, String userTag,
                         String userID, String reason, Date timestamp) {
        this.action = action;
        this.incidentID = incidentID;
        this.userTag = userTag;
        this.userID = userID;
        this.reason = reason;
        this.timestamp = timestamp;
    }

    public AuditLogEntry(AuditLogEmbed.Action action, String incidentID, String userTag,
                         String userID, String staffTag, String staffID, String reason,
                         Date timestamp) {
        this.action = action;
        this.incidentID = incidentID;
        this.userTag = userTag;
        this.userID = userID;
        this.staffTag = staffTag;
        this.staffID = staffID;
        this.reason = reason;
        this.timestamp = timestamp;
    }

    public AuditLogEntry(AuditLogEmbed.Action action, String incidentID, String userTag,
                         String userID, String reason) {
        this.action = action;
        this.incidentID = incidentID;
        this.userTag = userTag;
        this.userID = userID;
        this.reason = reason;
        this.timestamp = new Date();
    }

    public AuditLogEntry setAction(AuditLogEmbed.Action action) {
        this.action = action;
        return this;
    }

    public AuditLogEntry setIncidentID(String incidentID) {
        this.incidentID = incidentID;
        return this;
    }

    public AuditLogEntry setUserTag(String userTag) {
        this.userTag = userTag;
        return this;
    }

    public AuditLogEntry setUserID(String userID) {
        this.userID = userID;
        return this;
    }

    public AuditLogEntry setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public AuditLogEntry setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public AuditLogEntry setStaffTag(String staffTag) {
        this.staffTag = staffTag;
        return this;
    }

    public AuditLogEntry setStaffID(String staffID) {
        this.staffID = staffID;
        return this;
    }

    public String getUserTag() {
        return userTag;
    }

    public String getStaffTag() {
        return staffTag;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public AuditLogEmbed.Action getAction() {
        return action;
    }

    public String getIncidentID() {
        return incidentID;
    }

    public String getUserID() {
        return userID;
    }

    public String getReason() {
        return reason;
    }

    public String getStaffID() {
        return staffID;
    }

}
