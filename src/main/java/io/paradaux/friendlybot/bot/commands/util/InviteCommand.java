/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.utility.InviteCommand :  31/01/2021, 01:26
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
import io.paradaux.friendlybot.FriendlyBot;
import io.paradaux.friendlybot.core.utils.models.types.BaseCommand;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;

/**
 * This is a command which provides the user with an invite link to the current discord server.
 *
 * @author Rían Errity
 * @version Last modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 * @see FriendlyBot
 * */

public class InviteCommand extends BaseCommand {

    public InviteCommand(Logger logger) {
        super(logger);
        this.name = "invite";
        this.aliases = new String[]{"inv", "i"};
        this.help = "Provides the user with an invite link to invite the bot.";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();

        String inviteInfo = "This is a utility discord bot for moderating discord servers pertaining to computer science, with a "
                + "markov chain-backed artificial intelligence. This particular instance of the bot is hosted by Rían#6500.\n\n" + "There "
                + "is nothing stopping anyone from starting their own instance, but you will require an SMTP Login to make use " + "of the"
                + " email verification, and a mongodb database for everything else.";

        message.getChannel().sendMessage(inviteInfo).queue();
    }
}
