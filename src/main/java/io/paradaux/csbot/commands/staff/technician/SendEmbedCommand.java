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

package io.paradaux.csbot.commands.staff.technician;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.csbot.commands.staff.PrivilegedCommand;
import io.paradaux.csbot.embeds.*;
import io.paradaux.csbot.embeds.command.NoPermissionEmbed;
import io.paradaux.csbot.embeds.command.SyntaxErrorEmbed;
import io.paradaux.csbot.embeds.moderation.*;
import io.paradaux.csbot.embeds.modmail.ModMailSentEmbed;
import io.paradaux.csbot.embeds.notices.ModMailNoticeEmbed;
import io.paradaux.csbot.embeds.notices.RulesAcceptanceNoticeEmbed;
import io.paradaux.csbot.embeds.notices.VerificationNoticeEmbed;
import io.paradaux.csbot.embeds.roleselection.PoliticsOptionEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

public class SendEmbedCommand extends PrivilegedCommand {

    public SendEmbedCommand() {
        this.name = "sendembed";
        this.aliases = new String[]{"se"};
        this.help = "Sends the specified embed.";
    }

    @Override
    protected void execute(CommandEvent event) {
        String authorID = event.getAuthor().getId();
        if (isNotManagement(authorID)) return;

        System.out.println(this.getArguments());
        String[] args = event.getArgs().split(" ");

        Embed embed;

        switch (args[0].toLowerCase()) {
            case "nopermission": {
                embed = new NoPermissionEmbed(event.getAuthor(), args[2], args[3]);
                break;
            }

            case "syntaxerror": {
                embed = new SyntaxErrorEmbed(event.getAuthor(), args[2], args[3]);
                break;
            }

            case "banned": {
                embed = new BannedEmbed(args[1], args[2]);
                break;
            }

            case "chatfiltertriggered": {
                embed = new ChatFilterTriggeredEmbed(AuditLogEmbed.Action.valueOf(args[1]),
                        args[3], args[4], args[5]);
                break;
            }

            case "citerule": {
                embed = new CiteRuleEmbed(args[1]);
                break;
            }

            case "kicked": {
                embed = new KickedEmbed(args[1], args[2]);
                break;
            }

            case "timedout": {
                embed = new TimedOutEmbed(args[1], args[2]);
                break;
            }

            case "warning": {
                embed = new WarningEmbed(args[1], args[2]);
                break;
            }

            case "modmailsent": {
                embed = new ModMailSentEmbed(args[1], args[2]);
                break;
            }

            case "mdomailnotice": {
                embed = new ModMailNoticeEmbed();
                break;
            }

            case "rulesacceptancenotice": {
                embed = new RulesAcceptanceNoticeEmbed();
                break;
            }

            case "verificationnotice": {
                embed = new VerificationNoticeEmbed();
                break;
            }

            case "announcement": {
                embed = new AnnouncementEmbed();
                break;
            }

            case "auditlog": {
                embed = new AuditLogEmbed(AuditLogEmbed.Action.valueOf(args[1]), event.getAuthor(), args[2], args[3]);
                break;
            }

            case "rules": {
                embed = new RulesEmbed();
                break;
            }

            case "politicsrules": {
                embed = new PoliticsRulesEmbed();
                break;
            }

            case "politicsoption": {
                embed = new PoliticsOptionEmbed();
                break;
            }

            default: {
                event.getMessage().getChannel().sendMessage("Incorrect Embed Specified").queue();
                return;
            }
        }

        embed.sendEmbed((TextChannel) event.getMessage().getChannel());



    }
}
