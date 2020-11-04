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

package io.paradaux.csbot.listeners.message;

import io.paradaux.csbot.api.ModerationAction;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ChatFilterListener extends ListenerAdapter {

    // List of words which are completely unacceptable / inappropriate
    final public String[] kickList = new String[]{"nigger, nigga, niglet, kike, fag, faggot, fanny, kike", "retard", "retarded", "incest", "incest",
            "incestuous", "tranny", "transvestite", "coon", "uncle tom", "sissy", "nancy", "girlie", "sodomite", "nelly", "poofter", "quean", "batty boy",
            "nellie", "dyke", "lesbo", "fauxbians", "trannie", "heshe", "troon", "cuntboy", "hefemale", "shemale", "dickgirl", "chicks with dicls", "femboy",
            "incel", "muzzie", "osama"};

    // List of words which go against the ethos of the server.
    final public String[] warnList = new String[]{"newb, noob", "nub", "asshole", "you're stupid", "nazi", "nazism", "commie", "libtard", "tanker",
            "anarchist", "anarcho", "neo", "bolshevik", "communism", "jreg", "centard", "alibaba", "ali baba", "isis", "terrorist", "knacker", "nacker",
            "bot trotter", "bot-trotter", "bog irish", "prod", "proddie", "tinker", "gypsy", "horse-fucker", "jaffa", "snout", "fascist"};

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getAuthor().isBot()) return;

        String messageContent = event.getMessage().getContentRaw();
        String guildID = message.getGuild().getId();
        String discordID = message.getAuthor().getId();

        if (containsWarnableWord(messageContent)) {
            event.getChannel().sendMessage(ModerationAction.warnUser(guildID, discordID, "Illicit word use", messageContent)).queue();
        }

        if (containsKickableWord(messageContent)) {
            event.getChannel().sendMessage(ModerationAction.kickUser(guildID, discordID, "Illicit word use", messageContent)).queue();
        }

    }

    public boolean containsKickableWord(String input) {
        return Arrays.stream(kickList).anyMatch(input::contains);
    }

    public boolean containsWarnableWord(String input) {
        return Arrays.stream(warnList).anyMatch(input::contains);
    }

}
