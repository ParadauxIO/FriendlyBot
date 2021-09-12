/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.utility.MemeImagesCommand :  06/02/2021, 12:48
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

package io.paradaux.friendlybot.bot.commands.util;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.friendlybot.core.utils.TimeUtils;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.BaseCommand;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.EmbedBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;

public class MemeImagesCommand extends BaseCommand {

    private static final String GET_MEMES_API = "https://api.imgflip.com/get_memes";

    public MemeImagesCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "memeimages";
        this.help = "Shows every possible meme image";
    }

    @Override
    protected void execute(CommandEvent event) {
        HttpApi http = new HttpApi();
        HttpRequest request = http.plainRequest(GET_MEMES_API);

        http.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept((response) -> {
            JSONArray memes = new JSONObject(response.body())
                    .getJSONObject("data")
                    .getJSONArray("memes");

            for (int i = memes.length() - 1; i != 0; i--) {
                JSONObject meme = memes.getJSONObject(i);

                EmbedBuilder builder = new EmbedBuilder()
                        .setTitle(meme.getString("name"))
                        .setColor(NumberUtils.randomColor())
                        .setDescription("**ID:** " + meme.getString("id") + "\n**Line count:** " + meme.getInt("box_count"))
                        .setImage(meme.getString("url"));

                event.getChannel().sendMessage(builder.build()).queue();
            }

            EmbedBuilder builder = new EmbedBuilder()
                    .setTitle("FriendlyBot » Meme Images")
                    .setColor(0x009999)
                    .setThumbnail("https://cdn.paradaux.io/img/4siil.png")
                    .setDescription("The above is every image usable with the ;meme caption command. Be sure to use the ID and specify "
                            + "the same amount of lines as requested by the command.")
                    .addField(";meme images", "Get a list of these images sent to your DMs", false)
                    .addField(";meme caption 222403160 Meme Documentation | For you to read the documentation", "Caption memes by specifying the ID, then each line separated by a pipe (|)", false)
                    .setFooter("Last updated: " + TimeUtils.formatTime(new Date()), "https://cdn.paradaux.io/img/fteuv.png");

            event.getChannel().sendMessage(builder.build()).queue();
        }).join();
    }
}