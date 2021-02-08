/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.models.database.CounterEntry :  31/01/2021, 01:26
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

public class CounterEntry implements Serializable {

    @BsonProperty(value = "last_incident_id")
    private long lastIncidentID;

    @BsonProperty(value = "last_ticket_number")
    private long lastTickerNumber;

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
