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

package io.paradaux.friendlybot.commands.staff.technician;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.utils.models.objects.PrivilegedCommand;
import io.paradaux.friendlybot.utils.embeds.*;
import io.paradaux.friendlybot.utils.embeds.command.NoPermissionEmbed;
import io.paradaux.friendlybot.utils.embeds.command.SyntaxErrorEmbed;
import io.paradaux.friendlybot.utils.embeds.moderation.*;
import io.paradaux.friendlybot.utils.embeds.modmail.ModMailSentEmbed;
import io.paradaux.friendlybot.utils.embeds.notices.ModMailNoticeEmbed;
import io.paradaux.friendlybot.utils.embeds.notices.RulesAcceptanceNoticeEmbed;
import io.paradaux.friendlybot.utils.embeds.notices.VerificationNoticeEmbed;
import io.paradaux.friendlybot.utils.embeds.roleselection.PoliticsOptionEmbed;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.interfaces.Embed;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.slf4j.Logger;

public class SendEmbedCommand extends PrivilegedCommand {

    public SendEmbedCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager) {
        super(config, logger, permissionManager);
        this.name = "sendembed";
        this.aliases = new String[]{"se"};
        this.help = "Sends the specified embed.";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();

        String[] args = getArgs(event.getArgs());
        String authorID = event.getAuthor().getId();

        if (!getPermissionManager().isTechnician(authorID)) {
            respondNoPermission(message, "[Technician]");
            return;
        }

        if (args.length < 3) {
            respondSyntaxError(message, ";sendembed <embed>");
            return;
        }

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
                embed = new AuditLogEmbed(AuditLogEmbed.Action.valueOf(args[1]), event.getAuthor(),
                        args[2], args[3]);
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
                respondSyntaxError(message, ";sendembed <embed>");
                return;
            }
        }

        embed.sendEmbed((TextChannel) event.getMessage().getChannel());
    }
}
