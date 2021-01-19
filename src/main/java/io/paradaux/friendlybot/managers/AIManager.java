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

import io.paradaux.friendlybot.utils.ai.MarkovMegaHal;
import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

public class AIManager {

    // TODO complete

    private static AIManager instance = null;
    private final MarkovMegaHal chatterbot;
    private final Logger logger;

    public AIManager(Logger logger) {
        this.logger = logger;

        chatterbot = new MarkovMegaHal();
        instance = this;
    }

    public static AIManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

    public void loadTrainingData(File file)  {
        try {
            chatterbot.addDocument(file.toURI().toASCIIString());
        } catch (IOException ok) {
            // TODO do something with this
        }

    }

    public String generateMessage() {
        return chatterbot.getSentence();
    }

}
