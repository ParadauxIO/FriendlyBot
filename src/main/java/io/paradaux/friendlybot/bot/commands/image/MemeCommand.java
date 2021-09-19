/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.fun.MemeCommand :  05/02/2021, 07:05
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

package io.paradaux.friendlybot.bot.commands.image;

import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.bot.command.CommandBody;
import io.paradaux.friendlybot.bot.command.DiscordCommand;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import io.paradaux.friendlybot.core.utils.HttpUtils;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.friendlybot.core.utils.models.exceptions.VerificationException;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Command(name = "meme", description = "Meme generation!", permission = "command.meme", aliases = {"m"})
public class MemeCommand extends DiscordCommand {

    private static final String GET_MEMES_API = "https://api.imgflip.com/get_memes";
    public static final String CAPTION_MEMES_API = "https://api.imgflip.com/caption_image";

    @Override
    public void execute(FGuild guild, CommandBody body) {
        Message message = body.getMessage();

        switch (body.getArgs()[0]) {
            case "images": {
                message.reply("I'm sending you a list of available images in your DMs...").queue();

                HttpApi http = new HttpApi();

                HttpRequest request = http.plainRequest(GET_MEMES_API);
                http.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept((response) -> {
                    JSONArray memes = new JSONObject(response.body())
                            .getJSONObject("data")
                            .getJSONArray("memes");

                    List<MessageEmbed> embeds = new ArrayList<>();
                    EmbedBuilder embedBuilder = new EmbedBuilder();

                    for (int i = 0, fieldCount = 0; i < memes.length(); i++, fieldCount++) {
                        if (fieldCount == 24) {
                            embedBuilder.setColor(NumberUtils.randomColor());
                            embeds.add(embedBuilder.build());
                            embedBuilder = new EmbedBuilder();
                            fieldCount = 0;
                        }

                        JSONObject meme = memes.getJSONObject(i);
                        embedBuilder.addField("ID: " + meme.getString("id"), "Name: [" + meme.getString("name")
                                + "](" + meme.getString("url") + ")", false);
                    }

                    body.getUser().openPrivateChannel().queue((channel) -> {
                        for (final var embed : embeds) {
                            channel.sendMessageEmbeds(embed).queue();
                        }
                    });
                }).join();
                break;
            }

            case "caption": {
                if (body.getArgs().length < 5) {
                    syntaxError(message);
                    return;
                }

                String id = body.getArgs()[1];

                List<String> args2 = new ArrayList<>();
                Collections.addAll(args2, body.getArgs());

                args2.remove(0);
                args2.remove(0);

                String[] lines;

                try {
                    lines = String.join(" ", args2).split("\\|");
                } catch (IndexOutOfBoundsException exception) {
                    lines = new String[]{String.join(" ", args2)};
                }

                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();

                MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("template_id", id)
                        .addFormDataPart("username", getConfig().getImgflipUsername())
                        .addFormDataPart("password", getConfig().getImgflipPassword());

                for (int i = 0; i < lines.length; i++) {
                    bodyBuilder.addFormDataPart("text" + i, lines[i]);
                }

                RequestBody reqBody = bodyBuilder.build();

                Request request = new Request.Builder()
                        .url(CAPTION_MEMES_API)
                        .method("POST", reqBody)
                        .build();

                HttpUtils.sendAsync(client, request).thenAccept((response -> {
                    if (response.body() == null) {
                        throw new IllegalStateException("No response received.");
                    }

                    try (Reader reader = response.body().charStream()) {
                        int charInt;
                        StringBuilder strBuilder = new StringBuilder();
                        while ((charInt = reader.read()) != -1) {
                            strBuilder.append((char) charInt);
                        }

                        JSONObject responseJson = new JSONObject(strBuilder.toString());

                        EmbedBuilder builder = new EmbedBuilder()
                                .setImage(responseJson.getJSONObject("data").getString("url"))
                                .setColor(NumberUtils.randomColor())
                                .setFooter("For " + body.getUser().getName());

                        body.getChannel().sendMessage(builder.build()).queue();

                    } catch (IOException ok) {
                        getLogger().error("Error occurred whilst interacting with mailgun.");
                        throw new VerificationException();
                    }
                })).join();

                break;
            }

            default: {
               syntaxError(message);
                break;
            }

        }

    }



}
