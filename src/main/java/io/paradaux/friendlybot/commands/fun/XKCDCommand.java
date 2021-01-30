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

package io.paradaux.friendlybot.commands.fun;

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

public class XKCDCommand extends BaseCommand {

    private static final String XKCD_SEARCH_API = "https://relevantxkcd.appspot.com/process?action=xkcd&query=%s";
    private static final String XKCD_INFO_API = "https://xkcd.com/%s/info.0.json";
    private static final String XKCD_PAGE_FORMAT = "https://xkcd.com/%s/";
    private static final String XKCD_ICON = "https://webcomicshub.com/uploads/webcomics/xkcd-1280x1024.png";

    public XKCDCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "xkcd";
        this.help = "Links to the specified xkcd";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();
        String args = event.getArgs();

        if (args == null || args.isEmpty()) {
            respondSyntaxError(message, ";xkcd <query>");
            return;
        }

        HttpApi http = new HttpApi(getLogger());

        HttpRequest request = http.plainRequest(String.format(XKCD_SEARCH_API, args.replace(" ", "%20")));
        http.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept((response) -> {
            String returnedId = response.body().split(" ")[2].replaceAll("\n","");

            try {
                Integer.parseInt(returnedId);
            } catch (NumberFormatException ok) {
                return;
            }

            HttpRequest idInfoRequest = http.plainRequest(String.format(XKCD_INFO_API, returnedId));
            HttpResponse<String> idInfoResponse = http.sendSync(idInfoRequest, HttpResponse.BodyHandlers.ofString());

            JSONObject idInfo = new JSONObject(idInfoResponse.body());

            String link = String.format(XKCD_PAGE_FORMAT, returnedId);

            EmbedBuilder builder = new EmbedBuilder()
                    .setColor(NumberUtils.randomColor())
                    .setTitle("XKCD: " + idInfo.getString("safe_title"))
                    .setAuthor("Search: " + args, link, XKCD_ICON)
                    .setImage(idInfo.getString("img"))
                    .setFooter(idInfo.getString("alt"));

            message.getChannel().sendMessage(builder.build()).queue();

        }).join();

    }

}
