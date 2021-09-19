/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.fun.XKCDCommand :  31/01/2021, 01:26
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
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.json.JSONObject;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Command(name = "xkcd", description = "Links to the specified xkcd", permission = "command.xkcd")
public class XKCDCommand extends DiscordCommand {

    private static final String XKCD_SEARCH_API = "https://relevantxkcd.appspot.com/process?action=xkcd&query=%s";
    private static final String XKCD_INFO_API = "https://xkcd.com/%s/info.0.json";
    private static final String XKCD_PAGE_FORMAT = "https://xkcd.com/%s/";
    private static final String XKCD_ICON = "https://webcomicshub.com/uploads/webcomics/xkcd-1280x1024.png";

    @Override
    public void execute(FGuild guild, CommandBody body) {
        Message message = body.getMessage();
        String[] args = body.getArgs();

        if (args == null || args.length == 0) {
            syntaxError(message);
            return;
        }

        HttpApi http = new HttpApi(getLogger());

        HttpRequest request = http.plainRequest(String.format(XKCD_SEARCH_API, String.join("%20", args)));
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
