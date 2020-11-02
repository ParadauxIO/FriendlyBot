/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
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

package io.paradaux.csbot.api;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * EmailUtils provides various utilities pertaining to the email functionality of the discord-bot used for verifying @tcd.ie emails
 *
 * @author Rían Errity
 * @version Last Modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 * @see io.paradaux.csbot.CSBot
 * */

public class EmailUtils {

    /**
     * Checks against a regex pattern whether or not the email provided is valid
     * @param email The Email you wish to verify is valid
     * @return Whether or not the email is valid
     * */
    public boolean isValidEmail(String email) {
        Pattern emailValidator = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        if (email==null) return false;
        return emailValidator.matcher(email).matches();
    }

    /**
     * Gets the domain (the section after the @ sign) from an email address.
     * @param email The Email you wish to get the domain of
     * @return The Domain of an email. Returns null if the email is invalid
     * */
    @Nullable
    public String getEmailDomain(String email) {
        if (!isValidEmail(email)) return null;
        return email.substring(email.indexOf("@") + 1);
    }

    /**
     * Generates a six-digit code with leading zeros.
     * @return 6-digit number with leading zeroes.
     * */
    public String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

}
