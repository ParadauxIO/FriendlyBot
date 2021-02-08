/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.models.database.ModMailResponse :  31/01/2021, 01:26
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

public class ModMailResponse implements Serializable {

    protected static final long serialVersionUID = 1L;

    private String authorId;
    private String message;
    
    public ModMailResponse(String authorId, String message) {
        this.authorId = authorId;
        this.message = message;
    }

    public ModMailResponse() {
    
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getMessage() {
        return message;
    }

    public ModMailResponse setAuthorId(String authorId) {
        this.authorId = authorId;
        return this;
    }

    public ModMailResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
