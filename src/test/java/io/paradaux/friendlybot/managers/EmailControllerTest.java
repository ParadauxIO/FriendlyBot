/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.managers.EmailControllerTest :  31/01/2021, 01:26
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
        configurationEntry = new ConfigurationEntry();

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