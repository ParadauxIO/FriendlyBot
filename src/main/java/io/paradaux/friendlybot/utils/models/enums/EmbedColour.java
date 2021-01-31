/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.models.enums.EmbedColour :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.utils.models.enums;

import javax.annotation.Nullable;

public enum EmbedColour {

    INFO(0x1D3557),
    MODERATION(0x457B9D),
    AUTOMATIC(0xA8DADC),
    NEUTRAL(0xF1FAEE),
    ISSUE(0xA8DADC);

    public final int colour;
    EmbedColour(int colour) {
        this.colour = colour;
    }

    public int getColour() {
        return colour;
    }

    /**
     * Gets the constant equivalent of a given colour.
     * @param value Integer color
     * @return The Constant which equates to the provided colour, or null.
     * */
    @Nullable
    public EmbedColour valueOf(int value) {
        for (EmbedColour color : values()) {
            if (color.getColour() == value) {
                return color;
            }
        }
        return null;
    }

}
