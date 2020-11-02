/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.csbot.api;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import java.util.ArrayList;

class SMTPConnectionTest {

    Logger logger = LoggerFactory.getLogger(getClass());
    ConfigurationCache configurationCache = new ConfigurationCache("", "", "", "", new ArrayList<String>(), "", "",
            "verification@paradaux.io", "EmailPassword", "srv2.paradaux.io", "587");

    SMTPConnection smtpConnection = new SMTPConnection(configurationCache);

    @Test
    void sendTestEmail() {
        try {
            smtpConnection.sendVerificationEmail("rerrity@gmail.com", "69420");
        } catch (MessagingException e) {
            logger.error("There was an error sending the verification email", e);
        }
    }

}