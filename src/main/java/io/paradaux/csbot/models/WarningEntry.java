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

public class WarningEntry {

    @BsonProperty(value = "discord_id")
    String discordID;

    @BsonProperty(value = "issuer_id")
    String issuerID;

    @BsonProperty(value = "reason")
    String reason;

    @BsonProperty(value = "date_issued")
    Date dateIssued;

    public WarningEntry() { }

    public WarningEntry(String discordID, String issuerID, String reason, Date dateIssued) {
        this.discordID = discordID;
        this.issuerID = issuerID;
        this.reason = reason;
        this.dateIssued = dateIssued;
    }

    public String getDiscordID() {
        return discordID;
    }

    public WarningEntry setDiscordID(String discordID) {
        this.discordID = discordID;
        return this;
    }

    public String getIssuerID() {
        return issuerID;
    }

    public WarningEntry  setIssuerID(String issuerID) {
        this.issuerID = issuerID;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public WarningEntry setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public WarningEntry setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
        return this;
    }
}
