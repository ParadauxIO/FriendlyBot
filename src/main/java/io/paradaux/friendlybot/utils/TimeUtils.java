/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.TimeUtils :  06/02/2021, 18:56
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

import java.util.regex.Pattern;

public class TimeUtils {

    private static final Pattern TIME_SPLITTER = Pattern.compile("(?<=[a-zA-Z])");
    private static final long SECOND = 1000;
    private static final long MINUTE = SECOND * 60;
    private static final long HOUR = MINUTE * 60;
    private static final long DAY = HOUR * 24;
    private static final long WEEK = DAY * 7;

    /**
     * Parses milliseconds from a String, for example: 1w = 1000 * 60 * 60 * 24 * 7 milliseconds.
     * */
    public static long getTime(String input) {

        // In milliseconds.
        long time = 0;
        String[] inputs = TIME_SPLITTER.split(input);

        for (String str : inputs) {
            switch (str.charAt(str.length()-1)) {
                case 's': {
                    time += Long.parseLong(StringUtils.removeLetters(str)) * SECOND;
                    break;
                }

                case 'm': {
                    time += Long.parseLong(StringUtils.removeLetters(str)) * MINUTE;
                    break;
                }

                case 'h': {
                    time += Long.parseLong(StringUtils.removeLetters(str)) * HOUR;
                    break;
                }

                case 'd': {
                    time += Long.parseLong(StringUtils.removeLetters(str)) * DAY;
                    break;
                }

                case 'w': {
                    time += Long.parseLong(StringUtils.removeLetters(str)) * WEEK;
                    break;
                }

                default: {
                    throw new RuntimeException("Invalid time format");
                }
            }
        }

        return time;
    }

    /**
     * Convert milliseconds into a more human-friendly time string (hours, minutes, seconds)
     * */
    public static String millisecondsToDisplay(long input) {
        long weeks = input / WEEK;
        input -= weeks * WEEK;

        long days = input / DAY;
        input -= days * DAY;

        long hours = input / HOUR;
        input -= hours * HOUR;

        long minutes = input / MINUTE;
        input -= minutes * MINUTE;

        long seconds = input / SECOND;

        StringBuilder builder = new StringBuilder();

        if (weeks > 0) {
            builder.append(weeks)
                    .append(" week")
                    .append(weeks != 1 ? "s" : "")
                    .append(" ");
        }

        if (days > 0) {
            builder.append(days)
                    .append(" day")
                    .append(weeks != 1 ? "s" : "")
                    .append(" ");
        }

        if (hours > 0) {
            builder.append(hours)
                    .append(" hour")
                    .append(weeks != 1 ? "s" : "")
                    .append(" ");
        }

        if (minutes > 0) {
            builder.append(minutes)
                    .append(" minute")
                    .append(weeks != 1 ? "s" : "")
                    .append(" ");
        }

        if (seconds > 0) {
            builder.append(seconds)
                    .append(" second")
                    .append(weeks != 1 ? "s" : "")
                    .append(" ");
        }

        return builder.toString().trim();
    }

}
