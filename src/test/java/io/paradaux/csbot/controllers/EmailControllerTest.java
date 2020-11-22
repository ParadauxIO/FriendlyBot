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

package io.paradaux.csbot.controllers;

import io.paradaux.csbot.models.interal.ConfigurationEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;

class EmailControllerTest {

    static final LogController logController = new LogController();

    static final ConfigurationEntry ConfigurationEntry = new ConfigurationEntry();
    static ConfigurationController configurationController = new ConfigurationController();

    @BeforeAll
    static void setup() {
        ConfigurationEntry
                .setSmtpUser("verification@paradaux.io")
                .setSmtpPass("SMTP-PASS-HERE")
                .setSmtpHost("srv2.paradaux.io")
                .setSmtpPort("587");

        configurationController = new ConfigurationController(ConfigurationEntry);
        logController.initialise();
    }

    @Test
    public void invalidEmailTest() {
        LogController.getLogger().info("Testing EmailController#isValidEmail");
        Assertions.assertFalse(EmailController.isValidEmail("ytrregewf@we"), "Not a valid email");
        Assertions.assertTrue(EmailController.isValidEmail("someNonTCDEmail@gmail.com"), "is a valid email");
        Assertions.assertTrue(EmailController.isValidEmail("someTCDEmail@tcd.ie"), "is a valid email");
    }

    @Test
    public void getEmailDomainTest() {
        LogController.getLogger().info("Testing EmailController#getEmailDomain");
        Assertions.assertEquals(EmailController.getEmailDomain("someNonTCDEmail@gmail.com"), "gmail.com");
        Assertions.assertEquals(EmailController.getEmailDomain("someTCDEmail@tcd.ie"), "tcd.ie");
    }


    @Test
    public void sendVerificationEmailTest() throws MessagingException {
        LogController.getLogger().info("Testing EmailController#sendVerificationEmail");

        EmailController emailController = new EmailController();
        emailController.initialise();

        emailController.sendVerificationEmail("rerrity@gmail.com", "69420", "Rían#6500");
    }


}