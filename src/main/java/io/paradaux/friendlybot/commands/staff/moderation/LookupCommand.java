/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.staff.moderation.LookupCommand :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.commands.staff.moderation;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.utils.embeds.AuditLogEmbed;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.database.AuditLogEntry;
import io.paradaux.friendlybot.utils.models.database.BanEntry;
import io.paradaux.friendlybot.utils.models.database.KickEntry;
import io.paradaux.friendlybot.utils.models.database.WarningEntry;
import io.paradaux.friendlybot.utils.models.types.ModerationAction;
import io.paradaux.friendlybot.utils.models.types.PrivilegedCommand;
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

        if (!isStaff(event.getGuild(), authorID)) {
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

        ModerationAction action = auditLogEntry.getAction();

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
                        .setAuthor("Lookup Request: WARN")
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
