/*
 * Copyright (c) 2021 |  Rían Errity. GPLv3
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
