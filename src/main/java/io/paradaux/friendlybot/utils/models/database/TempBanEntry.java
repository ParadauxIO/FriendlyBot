/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.models.database.TempBanEntry :  06/02/2021, 18:12
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

public class TempBanEntry implements Serializable {

    protected static final long serialVersionUID = 1L;

    @BsonProperty(value = "incident_id")
    private String incidentId;

    @BsonProperty(value = "target_tag")
    private String userTag;

    @BsonProperty(value = "target_id")
    private String userId;

    @BsonProperty(value = "staff_tag")
    private String staffTag;

    @BsonProperty(value = "staff_id")
    private String staffId;

    @BsonProperty(value = "reason")
    private String reason;

    @BsonProperty(value = "timestamp")
    private Date timestamp;

    @BsonProperty(value = "expiry")
    private Date expiry;

    public TempBanEntry() {

    }

    public TempBanEntry(String incidentId, String userTag, String userId, String staffTag, String staffId, String reason, Date timestamp,
                        Date expiry) {
        this.incidentId = incidentId;
        this.userTag = userTag;
        this.userId = userId;
        this.staffTag = staffTag;
        this.staffId = staffId;
        this.reason = reason;
        this.timestamp = timestamp;
        this.expiry = expiry;
    }

    public String getIncidentId() {
        return incidentId;
    }

    public String getUserTag() {
        return userTag;
    }

    public String getUserId() {
        return userId;
    }

    public String getStaffTag() {
        return staffTag;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getReason() {
        return reason;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Date getExpiry() {
        return expiry;
    }

    public TempBanEntry setIncidentId(String incidentId) {
        this.incidentId = incidentId;
        return this;
    }

    public TempBanEntry setUserTag(String userTag) {
        this.userTag = userTag;
        return this;
    }

    public TempBanEntry setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public TempBanEntry setStaffTag(String staffTag) {
        this.staffTag = staffTag;
        return this;
    }

    public TempBanEntry setStaffId(String staffId) {
        this.staffId = staffId;
        return this;
    }

    public TempBanEntry setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public TempBanEntry setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public TempBanEntry setExpiry(Date expiry) {
        this.expiry = expiry;
        return this;
    }
}
