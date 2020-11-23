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

package io.paradaux.csbot.models;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.Date;

public class ModMailEntry {

    public enum ModMailStatus {
        OPEN, CLOSED, PENDING
    }

    @BsonProperty(value = "ticket_number")
    String ticketNumber;

    @BsonProperty(value = "status")
    ModMailStatus status;

    @BsonProperty(value = "user_tag")
    String userTag;

    @BsonProperty(value = "user_id")
    String userID;

    @BsonProperty(value = "modmail_method")
    String modmailMethod;

    @BsonProperty(value = "issue")
    String issue;

    @BsonProperty(value = "time_opened")
    Date timeOpened;

    @BsonProperty(value = "last_responded")
    Date lastResponded;

    public ModMailEntry() {

    }

    public ModMailEntry(String ticketNumber, ModMailStatus status, String userTag, String userID,
                        String modmailMethod, String issue, Date timeOpened, Date lastResponded) {
        this.ticketNumber = ticketNumber;
        this.status = status;
        this.userTag = userTag;
        this.userID = userID;
        this.modmailMethod = modmailMethod;
        this.issue = issue;
        this.timeOpened = timeOpened;
        this.lastResponded = lastResponded;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public ModMailStatus getStatus() {
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

    public ModMailEntry setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
        return this;
    }

    public ModMailEntry setStatus(ModMailStatus status) {
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
}
