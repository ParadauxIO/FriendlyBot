/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.fun.InspireCommand :  31/01/2021, 01:27
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

package io.paradaux.friendlybot.bot.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.BaseCommand;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.EmbedBuilder;
import org.slf4j.Logger;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class InspireCommand extends BaseCommand {

    private static final String INSPIRE_API = "https://inspirobot.me/api?generate=true";

    public InspireCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "inspire";
        this.aliases = new String[]{"inspireme", "im"};
        this.help = "Sends some heartwarming inspirational quotes!.";
    }

    @Override
    protected void execute(CommandEvent event) {

        HttpApi http = new HttpApi(getLogger());
        HttpRequest request = http.plainRequest(INSPIRE_API);

        http.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept((response) -> {
            EmbedBuilder builder = new EmbedBuilder()
                    .setImage(response.body())
                    .setColor(NumberUtils.randomColor())
                    .setFooter("For " + event.getAuthor().getName());

            event.getMessage().getChannel().sendMessage(builder.build()).queue();
        }).join();

    }
}
