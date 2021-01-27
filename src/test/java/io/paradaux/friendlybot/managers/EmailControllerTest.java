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

package io.paradaux.friendlybot.managers;

import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class EmailControllerTest {

    static Logger logger;
    static ConfigurationEntry configurationEntry;
    static ConfigManager configurationController;

    @BeforeAll
    static void setup() {
        configurationEntry = new ConfigurationEntry()
                .setSmtpUser("verification@paradaux.io")
                .setSmtpPass("SMTP-PASS-HERE")
                .setSmtpHost("srv2.paradaux.io")
                .setSmtpPort("587");

        logger = LoggerFactory.getLogger(EmailControllerTest.class);

        configurationController = new ConfigManager(configurationEntry, logger);
    }

    @Test
    public void invalidEmailTest() {
        logger.info("Testing EmailController#isValidEmail");
        Assertions.assertFalse(SMTPManager.isValidEmail("ytrregewf@we"), "Not a valid email");
        Assertions.assertTrue(SMTPManager.isValidEmail("someNonTCDEmail@gmail.com"), "is a valid email");
        Assertions.assertTrue(SMTPManager.isValidEmail("someTCDEmail@tcd.ie"), "is a valid email");
    }

    @Test
    public void getEmailDomainTest() {
        logger.info("Testing EmailController#getEmailDomain");
        Assertions.assertEquals(SMTPManager.getEmailDomain("someNonTCDEmail@gmail.com"), "gmail.com");
        Assertions.assertEquals(SMTPManager.getEmailDomain("someTCDEmail@tcd.ie"), "tcd.ie");
    }

}