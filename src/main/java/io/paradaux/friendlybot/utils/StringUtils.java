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

import javax.annotation.Nullable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

public class StringUtils {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("MMMM yyyy HH:mm:ss");

    private static final Pattern VALID_EMAIL = Pattern.compile("(?:[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+(?:\\"
            + ".[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+)*|\""
            + "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01"
            + "-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)"
            + "+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:"
            + "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}"
            + "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:"
            + "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09"
            + "\\x0b\\x0c\\x0e-\\x7f])+)\\])");

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

    /**
     * Checks against a regex pattern whether or not the email provided is valid.
     * @param email The Email you wish to verify is valid
     * @return Whether or not the email is valid
     * */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        return VALID_EMAIL.matcher(email.toLowerCase()).matches();
    }

    /**
     * Gets the domain (the section after the @ sign) from an email address.
     * @param email The Email you wish to get the domain of
     * @return The Domain of an email. Returns null if the email is invalid
     * */
    @Nullable
    public static String getEmailDomain(String email) {
        if (!isValidEmail(email)) {
            return null;
        }
        return email.substring(email.indexOf("@") + 1);
    }

    /**
     * Generates a six-digit code with leading zeros.
     * @return 6-digit number with leading zeroes.
     * */
    public static String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    /**
     * Create a basic authentication header
     * */
    public static String basicAuth(String user, String pass) {
        return Base64.getEncoder().encodeToString((user + ":" + pass).getBytes());
    }

    /**
     * Create URL-encoded parameters.
     * */
    public static String urlEncode(String part) {
        return URLEncoder.encode(part, StandardCharsets.UTF_8);
    }
}
