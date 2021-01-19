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

package io.paradaux.friendlybot.utils.models;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class StringUtils {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("MMMM yyyy HH:mm:ss");
    /**
     * Converts the specified string to Title Case
     * <br>
     * Title Case is a text format whereby the first letter of every word is capitalised.
     * @param text The text you wish to convert to Title Case
     * @return Text In Title Case
     * */
    public static String toTitleCase(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        // In case input is in some form of snake case.
        text = text.replace("_", " ");

        StringBuilder converted = new StringBuilder();

        boolean convertNext = true;
        for (char ch : text.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                ch = Character.toLowerCase(ch);
            }
            converted.append(ch);
        }

        return converted.toString();
    }

    public static String formatTime(OffsetDateTime time) {
        return formatTime(LocalDateTime.from(time));
    }

    public static String formatTime(LocalDateTime dateTime) {
        LocalDateTime time = LocalDateTime.from(dateTime.atOffset(ZoneOffset.UTC));
        return time.getDayOfMonth() + getDayOfMonthSuffix(time.getDayOfMonth()) + " " + time
                .format(TIME_FORMATTER) + " UTC";
    }


    /**
     * Gets the suffix for the a day in a month
     * Example: 1st
     *
     * @param n The day in the month to get a suffix.
     * @return The suffix for the day.
     */
    public static String getDayOfMonthSuffix(final int n) {
        if (n < 1 || n > 31) {
            throw new IllegalArgumentException("illegal day of month: " + n);
        }

        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }
}
