/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.models.database.ModMailEntry :  31/01/2021, 01:27
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

import io.paradaux.friendlybot.utils.models.enums.TicketStatus;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ModMailEntry implements Serializable {

    protected static final long serialVersionUID = 1L;

    @BsonProperty(value = "ticket_number")
    private String ticketNumber;

    @BsonIgnore
    @BsonProperty(value = "status")
    private TicketStatus status;

    @BsonProperty(value = "user_tag")
    private String userTag;

    @BsonProperty(value = "user_id")
    private String userID;

    @BsonProperty(value = "modmail_method")
    private String modmailMethod;

    @BsonProperty(value = "issue")
    private String issue;

    @BsonProperty(value = "time_opened")
    private Date timeOpened;

    @BsonProperty(value = "last_responded")
    private Date lastResponded;

    @BsonProperty(value = "responses")
    private List<ModMailResponse> responses;

    public ModMailEntry() {

    }

    public ModMailEntry(String ticketNumber, TicketStatus status, String userTag, String userID, String modmailMethod, String issue,
                        Date timeOpened, Date lastResponded, List<ModMailResponse> responses) {
        this.ticketNumber = ticketNumber;
        this.status = status;
        this.userTag = userTag;
        this.userID = userID;
        this.modmailMethod = modmailMethod;
        this.issue = issue;
        this.timeOpened = timeOpened;
        this.lastResponded = lastResponded;
        this.responses = responses;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public String getUserTag() {
        return userTag;
    }

    public String getUserID() {
        return userID;
    }

    public String getModmailMethod() {
        return modmailMethod;
    }

    public String getIssue() {
        return issue;
    }

    public Date getTimeOpened() {
        return timeOpened;
    }

    public Date getLastResponded() {
        return lastResponded;
    }

    public List<ModMailResponse> getResponses() {
        return responses;
    }

    public ModMailEntry setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
        return this;
    }

    public ModMailEntry setStatus(TicketStatus status) {
        this.status = status;
        return this;
    }

    public ModMailEntry setUserTag(String userTag) {
        this.userTag = userTag;
        return this;
    }

    public ModMailEntry setUserID(String userID) {
        this.userID = userID;
        return this;
    }

    public ModMailEntry setModmailMethod(String modmailMethod) {
        this.modmailMethod = modmailMethod;
        return this;
    }

    public ModMailEntry setIssue(String issue) {
        this.issue = issue;
        return this;
    }

    public ModMailEntry setTimeOpened(Date timeOpened) {
        this.timeOpened = timeOpened;
        return this;
    }

    public ModMailEntry setLastResponded(Date lastResponded) {
        this.lastResponded = lastResponded;
        return this;
    }

    public ModMailEntry setResponses(List<ModMailResponse> responses) {
        this.responses = responses;
        return this;
    }
}
