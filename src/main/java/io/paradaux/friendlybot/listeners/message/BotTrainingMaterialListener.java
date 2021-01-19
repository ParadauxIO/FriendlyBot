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

package io.paradaux.friendlybot.listeners.message;

import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.objects.DiscordEventListener;
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
