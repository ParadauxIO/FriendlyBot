/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.utility.GoogleCommand :  06/02/2021, 11:08
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

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.utils.StringUtils;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.types.BaseCommand;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GoogleCommand extends BaseCommand {

    private static final String QUERY_URL = "https://www.googleapis.com/customsearch/v1?key=%s&q=%s&safe=ACTIVE";
    private final String apiKey;

    public GoogleCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "google";
        this.aliases = new String[]{"googleit"};
        this.help = "Google via discord!";
        this.apiKey = "";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();
        String query = event.getArgs();

        if (query.isEmpty()) {
            respondSyntaxError(message, ";google <query>");
            return;
        }

        HttpApi http = new HttpApi(getLogger());
        HttpRequest request = http.jsonRequest(String.format(QUERY_URL, apiKey, StringUtils.urlEncode(query.replace(' ','+'))));

        http.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept((response -> {
            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();



        })).join();


    }
}
