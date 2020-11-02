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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailUtilsTest {

    Logger logger = LoggerFactory.getLogger(getClass());
    EmailUtils emailUtils = new EmailUtils();

    @Test
    public void invalidEmailTest() {
        logger.info("Testing EmailUtils#isValidEmail");
        Assertions.assertFalse(emailUtils.isValidEmail("ytrregewf@we"), "Not a valid email");
        Assertions.assertTrue(emailUtils.isValidEmail("rian@paradaux.io"), "is a valid email");
        Assertions.assertTrue(emailUtils.isValidEmail("errityr@tcd.ie"), "is a valid email");
    }

    @Test
    public void getEmailDomainTest() {
        logger.info("Testing EmailUtils#getEmailDomain");
        Assertions.assertEquals(emailUtils.getEmailDomain("rian@paradaux.io"), "paradaux.io");
        Assertions.assertEquals(emailUtils.getEmailDomain("errityr@tcd.ie"), "tcd.ie");
    }
}
