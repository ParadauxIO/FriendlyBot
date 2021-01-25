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

package io.paradaux.friendlybot.commands.staff.moderation;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.utils.StringUtils;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.database.ModMailEntry;
import io.paradaux.friendlybot.utils.models.enums.EmbedColour;
import io.paradaux.friendlybot.utils.models.objects.PrivilegedCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;

public class TicketCommand extends PrivilegedCommand {

    public TicketCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager) {
        super(config, logger, permissionManager);
        this.name = "ticket";
        this.aliases = new String[]{"t"};
        this.help = "Provides ticket-related utilities.";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();
        String[] args = getArgs(event.getArgs());

        String ticketNumber;

        if (!isStaff(message.getAuthor().getId())) {
            respondNoPermission(message, "[Moderator, Administrator]");
            return;
        }

        if (args.length <= 1) {
            respondSyntaxError(message, ";ticket <close/open/view> [ticketnumber]");
            return;
        }

        MongoManager mongo = MongoManager.getInstance();

        switch (args[0]) {

            case "close": {
                ticketNumber = args[1];
                ModMailEntry entry = mongo.getModMailEntry(ticketNumber);

                Member member = retrieveMember(message.getGuild(), entry.getUserID());

                message.getChannel().sendMessage("Ticket involving: `" + entry.getIssue() + "` has been closed.").queue();
                mongo.setModMailStatus(ticketNumber, ModMailEntry.ModMailStatus.CLOSED);

                if (member == null) {
                    message.getChannel().sendMessage("The user that opened this ticket is no longer in the guild.\n"
                            + "They were not notified of the closure.").queue();
                    return;
                }

                member.getUser().openPrivateChannel().queue((privateChannel -> {
                    privateChannel.sendMessage("Your ticket involving: `" + entry.getIssue() + "` has been been marked as closed.").queue();
                }));

                break;
            }

            case "view": {
                ticketNumber = args[1];

                ModMailEntry entry = mongo.getModMailEntry(ticketNumber);

                if (entry == null) {
                    message.getChannel().sendMessage("The specified ticket was not found.").queue();
                    return;
                }

                EmbedBuilder embedBuilder = new EmbedBuilder();

                embedBuilder.setColor(EmbedColour.MODERATION.getColour())
                        .addField("User", entry.getUserTag(), true)
                        .addField("Status", entry.getStatus().toString(), true)
                        .addField("User Id", entry.getUserID(), true)
                        .addField("Ticket Id", entry.getTicketNumber(), true)
                        .addField("Method", entry.getModmailMethod(), true)
                        .addField("Opened", StringUtils.formatTime(entry.getTimeOpened()), true)
                        .addField("Last Responded", StringUtils.formatTime(entry.getTimeOpened()), true)
                        .addField("Issue", entry.getIssue(), false);

                event.getChannel().sendMessage(embedBuilder.build()).queue();
                break;
            }

            case "open": {
                ticketNumber = args[1];
                ModMailEntry entry = mongo.getModMailEntry(ticketNumber);

                Member member = retrieveMember(message.getGuild(), entry.getUserID());

                message.getChannel().sendMessage("Ticket involving: `" + entry.getIssue() + "` has been re-opened.").queue();
                mongo.setModMailStatus(ticketNumber, ModMailEntry.ModMailStatus.OPEN);

                if (member == null) {
                    message.getChannel().sendMessage("The user that opened this ticket is no longer in the guild.\n"
                            + "They were not notified of the re-opening.").queue();
                    return;
                }

                member.getUser().openPrivateChannel().queue((privateChannel -> {
                    privateChannel.sendMessage("Your ticket involving: `" + entry.getIssue() + "` has been been re-opened.").queue();
                }));

                break;
            }

            default: {
                respondSyntaxError(message, ";ticket <close/open/view> [ticketnumber]");
            }

        }


    }
}
