/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.fun.EightBallCommand :  06/02/2021, 09:41
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

package io.paradaux.friendlybot.discord.commands.fun;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.friendlybot.core.utils.RandomUtils;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.BaseCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class EightBallCommand extends BaseCommand {

    private static final String WAITING_IMAGE = "https://i.imgur.com/nBRPBMf.gif";
    private final JsonObject eightBallResponses;

    public EightBallCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "eightball";
        this.help = "Asks the all knowing 8ball";
        this.aliases = new String[]{"8b", "8ball"};

        InputStream rawResponseData = getClass().getResourceAsStream("/data/8ball.json");
        JsonReader reader = new JsonReader(new InputStreamReader(rawResponseData));

        this.eightBallResponses = new Gson().fromJson(reader, JsonObject.class);

        if (eightBallResponses == null) {
            throw new RuntimeException("Eight ball responses not found.");
        }
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();

        if (event.getArgs().isEmpty()) {
            respondSyntaxError(message, ";8ball <question>");
        }

        RandomUtils rUtils = new RandomUtils(new Random());

        String type = rUtils.fromCollection(eightBallResponses.keySet());

        if (type == null) {
            getLogger().error("There was an issue while gathering a random response.");
            return;
        }

        JsonArray possibleResponses = eightBallResponses.getAsJsonArray(type);

        if (possibleResponses == null) {
            getLogger().error("There was an issue while gathering a random response.");
            return;
        }

        JsonElement responseElement = rUtils.fromJsonArray(possibleResponses);

        if (responseElement == null) {
            getLogger().error("There was an issue while gathering a random response.");
            return;
        }

        String response = responseElement.getAsString();

        MessageEmbed waitingEmbed = new EmbedBuilder()
                .setColor(NumberUtils.randomColor())
                .setTitle("Shaking the magic ball...")
                .setImage(WAITING_IMAGE)
                .build();

        int color = 0x000000;
        switch (type) {
            case "affirmative": {
                color = 0x00FF00;
                break;
            }

            case "non-committal": {
                color = 0xDA9F20;
                break;
            }

            case "negative": {
                color = 0xFF0000;
                break;
            }
        }

        MessageEmbed responseEmbed = new EmbedBuilder()
                .setColor(color)
                .setTitle(response)
                .build();

        event.getChannel().sendMessage(waitingEmbed).queue((sentMessage) -> {
            try {
                TimeUnit.SECONDS.sleep(4);
                sentMessage.editMessage(responseEmbed).queue();
            } catch (InterruptedException e) {
                getLogger().error("Interrupted whilst shaking the magic eight ball..");
            }

        });

    }
}
