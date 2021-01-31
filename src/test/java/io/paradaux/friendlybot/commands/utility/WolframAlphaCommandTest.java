/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.utility.WolframAlphaCommandTest :  31/01/2021, 04:17
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

package io.paradaux.friendlybot.commands.utility;

import io.paradaux.friendlybot.utils.NumberUtils;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.EmbedBuilder;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class WolframAlphaCommandTest {

    private static final String WOLFRAM_API = "http://api.wolframalpha.com/v1/simple?appid=%s&i=%s";
    private static final String IMGUR_API = "https://api.imgur.com/3/upload/";
    private static Logger logger = null;

    @BeforeEach
    void setUp() {
        logger = LoggerFactory.getLogger(getClass());
    }

    @Test
    void getImageContent() {
        HttpApi http = new HttpApi(logger);
        HttpRequest request = http.plainRequest("https://cdn.paradaux.io/img/r8ywk.png");
        http.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray()).thenAccept((response) -> {
            byte[] bytes = response.body();
            System.out.println(bytes.length);
            try (FileOutputStream fos = new FileOutputStream("src/test/resources/img.png")) {
                fos.write(bytes);
            } catch (IOException exception) {
                logger.error("An error occurred.");
            }
        }).join();
    }

    @Test
    void wolframAlphaGet() {
        HttpApi http = new HttpApi(logger);
        HttpRequest request = http.plainRequest("https://api.wolframalpha.com/v1/simple?appid=DEMO&i=What+airplanes+are+flying+overhead%3F");
        http.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray()).thenAccept((response) -> {
            byte[] bytes = response.body();
            System.out.println(bytes.length);
            try (FileOutputStream fos = new FileOutputStream("src/test/resources/img2.png")) {
                fos.write(bytes);
            } catch (IOException exception) {
                logger.error("An error occurred.");
            }
        }).join();
    }

    @Test
    void getAndPostToImgur() {
        // Get's the image from WA
        HttpApi http = new HttpApi(logger);

        HttpRequest request = http.plainRequest("https://api.wolframalpha.com/v1/simple?appid=DEMO&i=What+airplanes+are+flying+overhead%3F");
        http.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray()).thenAccept((response) -> {

            // Send that image to imgur
            String[] headers = {"Authorization", "Client-ID 0b7a7e68cbb38a4"};
            HttpRequest request2 = http.postBytes(IMGUR_API, response.body(), headers);

            HttpResponse<String> response2 = http.sendSync(request2, HttpResponse.BodyHandlers.ofString());
            JSONObject imgurMeta = new JSONObject(response2.body());
            System.out.println(response2.body());

            EmbedBuilder builder = new EmbedBuilder()
                    .setAuthor("Wolfram Alpha Query.", "https://wolframalpha.com", "https://www.wolframalpha.com/_next/static/images/share_3G6HuGr6.png")
                    .setColor(NumberUtils.randomColor())
                    .setImage(imgurMeta.getString("link"))
                    .setFooter("Query: " + "");


        }).join();

    }
}