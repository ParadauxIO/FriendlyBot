/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.fun.LmgtfyCommand :  06/02/2021, 11:08
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

package io.paradaux.friendlybot.bot.commands.joke;

import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.bot.command.CommandBody;
import io.paradaux.friendlybot.bot.command.DiscordCommand;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

@Command(name = "lmgtfy", description = "Let me lmgtfy for you...", permission = "command.lmgtfy")
public class LmgtfyCommand extends DiscordCommand {

    private static final String LMGTFY_LINK = "https://lmgtfy.com/?q=";

    @Override
    public void execute(FGuild guild, CommandBody body) {
        Message message = body.getMessage();
        String query = String.join(" ", body.getArgs());

        if (query.isEmpty()) {
            syntaxError(message);
            return;
        }

        String url = (LMGTFY_LINK + query.replace(' ', '+'));
        EmbedBuilder embed = new EmbedBuilder();
        embed.setAuthor("Click me!", url, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Google_%22G%22_Logo.svg/1200px-Google_%22G%22_Logo.svg.png");
        embed.setColor(0x17E77E);
        message.getChannel().sendMessage(embed.build()).queue();
    }
}
