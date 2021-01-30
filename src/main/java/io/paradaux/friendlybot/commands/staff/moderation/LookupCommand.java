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
import io.paradaux.friendlybot.utils.models.types.PrivilegedCommand;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.utils.embeds.AuditLogEmbed;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.utils.models.database.AuditLogEntry;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.database.BanEntry;
import io.paradaux.friendlybot.utils.models.database.KickEntry;
import io.paradaux.friendlybot.utils.models.database.WarningEntry;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;

public class LookupCommand extends PrivilegedCommand {

    public LookupCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager) {
        super(config, logger, permissionManager);
        this.name = "lookup";
        this.help = "Retrieves a moderation file from the database.";
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

        if (args.length < 1) {
            respondSyntaxError(message, ";lookup <incidentid>");
            return;
        }

        MongoManager mongo = MongoManager.getInstance();

        AuditLogEntry auditLogEntry = mongo.getAuditLogEntry(args[0]);

        if (auditLogEntry == null) {
            respondSyntaxError(message, ";lookup <incidentid>");
            return;
        }

        AuditLogEmbed.Action action = auditLogEntry.getAction();

        EmbedBuilder builder = new EmbedBuilder();

        switch (action) {
            case BAN: {
                BanEntry banEntry = mongo.getBanEntry(args[0]);

                if (banEntry == null) {
                    message.getChannel().sendMessage("Nothing was found.").queue();
                    return;
                }

                builder.addField("User", banEntry.getUserTag(), true)
                        .addField("UserID", banEntry.getUserID(), true)
                        .addBlankField(true)
                        .addField("Staff", banEntry.getStaffTag(), true)
                        .addField("StaffID", banEntry.getStaffID(), true)
                        .addBlankField(true)
                        .addField("Reason", banEntry.getReason(), false)
                        .setColor(0x000000)
                        .setAuthor("Lookup Request: BAN")
                        .setFooter("A copy of the audit log entry for this event "
                                + "has been messaged to you.");

                break;
            }
            case KICK: {
                KickEntry kickEntry = mongo.getKickEntry(args[0]);

                if (kickEntry == null) {
                    message.getChannel().sendMessage("Nothing was found.").queue();
                    return;
                }

                builder.addField("User", kickEntry.getUserTag(), true)
                        .addField("UserID", kickEntry.getUserID(), true)
                        .addBlankField(true)
                        .addField("Staff", kickEntry.getStaffTag(), true)
                        .addField("StaffID", kickEntry.getStaffID(), true)
                        .addBlankField(true)
                        .addField("Reason", kickEntry.getReason(), false)
                        .setColor(0x000000)
                        .setAuthor("Lookup Request: KICK")
                        .setFooter("A copy of the audit log entry for this event has "
                                + "been messaged to you.");
                break;
            }

            case WARN: {
                WarningEntry warningEntry = mongo.getWarningEntry(args[0]);

                if (warningEntry == null) {
                    message.getChannel().sendMessage("Nothing was found.")
                            .queue();

                    return;
                }

                builder.addField("User", warningEntry.getUserTag(), true)
                        .addField("UserID", warningEntry.getUserID(), true)
                        .addBlankField(true)
                        .addField("Staff", warningEntry.getStaffTag(), true)
                        .addField("StaffID", warningEntry.getStaffID(), true)
                        .addBlankField(true)
                        .addField("Reason", warningEntry.getReason(), false)
                        .setColor(0x000000)
                        .setAuthor("Lookup Request: KICK")
                        .setFooter("A copy of the audit log entry for this event has "
                                + "been messaged to you.");
                break;
            }

            default: {
                getLogger().error("Audit Log lookup failed for {}.", event.getAuthor().getAsTag());
                break;
            }

        }

        message.getChannel().sendMessage(builder.build()).queue();

        User user = message.getJDA().getUserById(auditLogEntry.getUserID());
        User staff = message.getJDA().getUserById(auditLogEntry.getStaffID());

        if (user == null || staff == null) {
            message.getChannel().sendMessage("An unknown error occurred.").queue();
            return;
        }

        AuditLogEmbed embed = new AuditLogEmbed(auditLogEntry.getAction(), user,
                staff, auditLogEntry.getReason(), auditLogEntry.getIncidentID());

        message.getAuthor().openPrivateChannel().queue((channel) -> channel
                .sendMessage(embed.getEmbed())
                .queue());



    }
}
