/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.listeners.ai.BotTrainingMaterialListener :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.listeners.ai;

import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.types.DiscordEventListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.List;

public class BotTrainingMaterialListener extends DiscordEventListener {

    // Sentences with these words in them won't be used to train the algorithm.
    private static final String[] FILTER = new String[]{ "God", "Goddamn", "anal", "anus", "arse", "ass", "balls", "ballsack", "bastard",
            "biatch", "bitch", "bloody", "blowjob", "bollock", "bollok", "boner", "boob", "bugger", "bum", "butt", "buttplug", "clitoris",
            "cock", "communist", "coon", "crap", "cunt", "damn", "democrat", "dick", "dildo", "dukes", "dyke", "end", "fag", "feck",
            "felching", "fellate", "fellatio", "flange", "fuck", "fudge", "fudgepacker", "hell", "homo", "ira", "jerk", "jizz", "jonathan",
            "ken", "knob", "knobend", "labia", "meriel", "muff", "nigga", "nigger", "omg", "packer", "penis", "piss", "poop", "prick",
            "pube", "pussy", "queer", "republican", "savage", "scrotum", "sex", "shit", "slut", "smegma", "spunk", "tit", "tosser", "turd",
            "twat", "vagina", "wank", "whore", "wtf"};

    private static final List<String> FILTER_LIST = List.of(FILTER);

    public BotTrainingMaterialListener(ConfigurationEntry config, Logger logger) {
        super(config, logger);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        // TODO implement
    }

    private String filterMessage(String message) {
        return "";
        // TODO implement

    }



}
