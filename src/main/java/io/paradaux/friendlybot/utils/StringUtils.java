/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.StringUtils :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    public static String formatTime(Date date) {
        return formatTime(date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
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
