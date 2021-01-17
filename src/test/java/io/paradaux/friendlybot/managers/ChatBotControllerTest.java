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

package io.paradaux.friendlybot.managers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

class ChatBotControllerTest {

    private static final String RESOURCES_FOLDER = "src/test/resources/";

    private static final String[] TRAINING_DATA = {"cswikipedia", "navysealcopypasta", "to-publiceducation", "to-trumpleavingwhitehouse",
            "ww-useventilator"};
    static Logger logger;
    static AIController controller;

    @BeforeAll
    static void setUp() {
        logger = LoggerFactory.getLogger(ChatBotControllerTest.class);
        controller = new AIController(logger);

        for (String str : TRAINING_DATA) {
            controller.loadTrainingData(new File(RESOURCES_FOLDER + str));
        }


    }

    @Test
    void generateSentenceSeeded() {
        String seed = "Are you a real boy?";
//        LogManager.getLogger().info(controller.generateMessage(seed));
    }
}