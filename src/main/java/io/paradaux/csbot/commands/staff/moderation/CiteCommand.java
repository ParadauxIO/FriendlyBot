/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
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

package io.paradaux.csbot.commands.staff.moderation;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.csbot.commands.staff.PrivilegedCommand;
import io.paradaux.csbot.interfaces.Embed;
import io.paradaux.csbot.embeds.moderation.CiteRuleEmbed;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class CiteCommand extends PrivilegedCommand {

    public CiteCommand() {
        this.name = "cite";
        this.help = "Cites a certain rule to remind users of the rules which we have in place.";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();

        String[] args = getArgs(event.getArgs());
        String authorID = event.getAuthor().getId();

        if (!isStaff(authorID)) {
            respondNoPermission(message, "[Moderator, Administrator]");
            return;
        }

        if (args.length < 2 || message.getMentionedChannels().size() == 0) {
            respondSyntaxError(message, ";cite <channel> <section>");
            return;
        }

        TextChannel channel = message.getMentionedChannels().get(0);

        Embed citeRuleEmbed = new CiteRuleEmbed(args[0]);

        message.getChannel().sendMessage("Citation sent.").queue();
        citeRuleEmbed.sendEmbed(channel);
    }
}
