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

package io.paradaux.friendlybot.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.paradaux.friendlybot.utils.embeds.command.UserInfoEmbed;
import io.paradaux.friendlybot.utils.models.interfaces.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;

class EmbedParserTest {

    public static final String EMBED_DIRECTORY = "/embeds/";
    private static Embed embed;
    private static String embedName;

    @BeforeAll
    public static void setup() {
        embedName = "useraccountinfo";
        embed = new UserInfoEmbed("%user_tag%", "https://example.org", "%user_status%",
                "%user_datecreated%", "%user_datejoined%", "%user_roles%", "%user_nickname%");
    }

    @Test
    void serialise() {
        saveFile(EMBED_DIRECTORY + embedName + ".json", embed.getBuilder());
    }

    @Test
    void deSerialise() throws FileNotFoundException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(EMBED_DIRECTORY + embedName + ".json"));
        EmbedBuilder builder = gson.fromJson(bufferedReader, EmbedBuilder.class);

        System.out.println(builder.toString());
    }

    public static void saveFile(String fileName, Object src) {
        GsonBuilder gBuilder = new GsonBuilder().setPrettyPrinting();
        Gson gson = gBuilder.create();
        String prettyJsonString = gson.toJson(src);

        FileWriter fw;
        try {
            fw = new FileWriter(fileName, false);
            fw.write(prettyJsonString);
            fw.close();
        } catch (IOException e) {
           throw new RuntimeException("Error occurred!");
        }
    }

}