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

package io.paradaux.friendlybot.models.interal;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class CounterEntry {

    @BsonProperty(value = "last_incident_id")
    Long lastIncidentID;

    @BsonProperty(value = "last_ticket_number")
    Long lastTickerNumber;

    public CounterEntry(Long lastIncidentID, Long lastTickerNumber) {
        this.lastIncidentID = lastIncidentID;
        this.lastTickerNumber = lastTickerNumber;
    }

    public CounterEntry() {

    }

    public Long getLastIncidentID() {
        return lastIncidentID;
    }

    public Long getLastTickerNumber() {
        return lastTickerNumber;
    }

    public CounterEntry setLastIncidentID(Long lastIncidentID) {
        this.lastIncidentID = lastIncidentID;
        return this;
    }

    public CounterEntry setLastTickerNumber(Long lastTickerNumber) {
        this.lastTickerNumber = lastTickerNumber;
        return this;
    }
}
