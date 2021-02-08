/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.models.database.MessageEntry :  03/02/2021, 01:08
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

public class MessageEntry implements Serializable {

    @BsonProperty(value = "author_id")
    private String authorId;

    @BsonProperty(value = "message_id")
    private String messageId;

    @BsonProperty(value = "message_content")
    private String content;

    @BsonProperty(value = "message_channel")
    private String channel;

    @BsonProperty(value = "message_newcontent")
    private String newContent;

    @BsonProperty(value = "date_sent")
    private Date date;

    public MessageEntry() {

    }

    public MessageEntry(String authorId, String messageId, String content, String channel, String newContent, Date date) {
        this.authorId = authorId;
        this.messageId = messageId;
        this.content = content;
        this.channel = channel;
        this.newContent = newContent;
        this.date = date;
    }

    public MessageEntry setAuthorId(String authorId) {
        this.authorId = authorId;
        return this;
    }

    public MessageEntry setMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public MessageEntry setContent(String content) {
        this.content = content;
        return this;
    }

    public MessageEntry setDate(Date date) {
        this.date = date;
        return this;
    }

    public MessageEntry setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public MessageEntry setNewContent(String newContent) {
        this.newContent = newContent;
        return this;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public String getChannel() {
        return channel;
    }

    public String getNewContent() {
        return newContent;
    }
}
