/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.managers.AIManager :  31/01/2021, 01:26
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

import io.paradaux.ai.MarkovMegaHal;
import io.paradaux.friendlybot.utils.models.database.MessageEntry;
import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AIManager {

    private static AIManager instance = null;
    private final MarkovMegaHal chatterbot;
    private final Logger logger;

    public AIManager(Logger logger) {
        this.logger = logger;

        chatterbot = new MarkovMegaHal();
        loadTrainingDataFromDatabase();

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
            logger.error("Error while adding " + file.getName() + " to the chatterbot's brain");
        }

    }

    public void addMessage(String str) {
        chatterbot.add(str);
    }

    public void loadTrainingDataFromDatabase() {
        for (MessageEntry m : MongoManager.getInstance().getAiMessages()) {
            logger.info("Loading message: " + m.getContent() + "...");
            chatterbot.add(m.getContent());
        }
    }

    public String generateMessage() {
        return chatterbot.getSentence();
    }

    public String generateMessage(String targetWord) {
        return chatterbot.getSentence(targetWord);
    }

}
