/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.utility.WolframAlphaCommand :  31/01/2021, 01:26
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

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.utils.NumberUtils;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.types.BaseCommand;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WolframAlphaCommand extends BaseCommand {

    private static final String WOLFRAM_API = "https://api.wolframalpha.com/v1/result?i=%s&appid=%s&";
    private static final String WOLFRAM_USER_LINK = "https://www.wolframalpha.com/input/?i=%s";
    private static final String IMGUR_API = "https://api.imgur.com/3/upload/";
    private static final String WOLFRAM_ICON = "https://www.wolframalpha.com/_next/static/images/share_3G6HuGr6.png";

    public WolframAlphaCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "wolframalpha";
        this.aliases = new String[]{"wa"};
        this.help = "Queries WolframAlpha's API.";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();
        String args = event.getArgs();

        if (args == null || args.isEmpty()) {
            respondSyntaxError(message, ";wa <query>");
            return;
        }

        HttpApi http = new HttpApi(getLogger());

        // Get's the image from WA
        HttpRequest request = http.plainRequest(String.format(WOLFRAM_API, args.replace(" ", "%20"), getConfig().getWolframAlphaApplicationId()));
        http.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray()).thenAccept((response) -> {
            // Send that image to imgur
            String[] headers = {"Authorization", "Client-ID " + getConfig().getImgurClientId()};
            HttpRequest request2 = http.postBytes(IMGUR_API, response.body(), headers);

            HttpResponse<String> response2 = http.sendSync(request2, HttpResponse.BodyHandlers.ofString());
            JSONObject imgurMeta = new JSONObject(response2.body());

            EmbedBuilder builder = new EmbedBuilder()
                    .setAuthor("Wolfram Alpha Query.", String.format(WOLFRAM_USER_LINK, args.replace(" ", "%20")), WOLFRAM_ICON)
                    .setColor(NumberUtils.randomColor())
                    .setImage(imgurMeta.getJSONObject("data").getString("link"))
                    .setFooter("Query: " + args);

            message.getChannel().sendMessage(builder.build()).queue();
        }).join();

    }

}
