/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.models.configuration.PermissionEntry :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.core.utils.models.configuration;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PermissionEntry implements Serializable {

    protected static final long serialVersionUID = 1L;

    @SerializedName("moderators")
    private ArrayList<String> moderators;

    @SerializedName("administrators")
    private ArrayList<String> administrators;

    @SerializedName("technicians")
    private ArrayList<String> technicians;

    public PermissionEntry() {

    }

    public PermissionEntry(ArrayList<String> moderators, ArrayList<String> administrators,
                           ArrayList<String> technicians) {
        this.moderators = moderators;
        this.administrators = administrators;
        this.technicians = technicians;
    }

    public ArrayList<String> getModerators() {
        return moderators;
    }

    public ArrayList<String> getAdministrators() {
        return administrators;
    }

    public ArrayList<String> getTechnicians() {
        return technicians;
    }

    public PermissionEntry setModerators(ArrayList<String> moderators) {
        this.moderators = moderators;
        return this;
    }

    public PermissionEntry setAdministrators(ArrayList<String> administrators) {
        this.administrators = administrators;
        return this;
    }

    public PermissionEntry setTechnicians(ArrayList<String> technicians) {
        this.technicians = technicians;
        return this;
    }
    
}
