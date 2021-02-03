/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.EmbedParserTest :  31/01/2021, 01:26
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