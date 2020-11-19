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
import io.paradaux.csbot.controllers.*;
import io.paradaux.csbot.embeds.AuditLogEmbed;
import io.paradaux.csbot.embeds.moderation.WarningEmbed;
import io.paradaux.csbot.models.interal.ConfigurationEntry;
import io.paradaux.csbot.models.moderation.WarningEntry;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;

/**
 * This is a command which
 *
 * @author Rían Errity
 * @version Last modified for 0.1.0-SNAPSHOT
 * @since 4/11/2020 DD/MM/YY
 * @see io.paradaux.csbot.CSBot
 * */

public class WarnCommand extends PrivilegedCommand {

    // Dependencies
    private static final ConfigurationEntry configurationEntry = ConfigurationController.getConfigurationEntry();
    private static final Logger logger = LogController.getLogger();
    private static final PermissionController permissionController = PermissionController.INSTANCE;

    public WarnCommand() {
        this.name = "warn";
        this.aliases = new String[]{"w"};
        this.help = "Warns the specified user";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();

        String[] args = getArgs(event.getArgs());
        String authorID = event.getAuthor().getId();

        if (!isStaff(authorID)) { respondNoPermission(message, "[Moderator, Administrator]"); return; }
        if (args.length < 2) { respondSyntaxError(message, ";ban <userid/@mention> <reason>"); return; }

        User target = parseTarget(message, 0, args);

        if (target == null) {
            message.getChannel().sendMessage("You did not specify a (valid) target.").queue();
            return;
        }

        if (isStaff(target.getId())) {
            message.getChannel().sendMessage("You cannot ban a staff member.").queue();
            return;
        }

        String incidentID = DatabaseController.INSTANCE.getNextIncidentID();
        String reason = parseSentance(1, args);

        WarningEmbed embed = new WarningEmbed(reason, incidentID);

        WarningEntry entry = new WarningEntry()
                .setIncidentID(incidentID)
                .setReason(reason)
                .setStaffID(authorID)
                .setStaffTag(event.getAuthor().getAsTag())
                .setUserID(target.getId())
                .setUserTag(target.getAsTag());

        DatabaseController.INSTANCE.addWarnEntry(entry);
        AuditLogController.INSTANCE.log(AuditLogEmbed.Action.WARN, target, event.getAuthor(), reason, incidentID);

        message.getChannel().sendMessage("Incident ID: " + incidentID + "\nReason: " + reason).queue();
        target.openPrivateChannel().queue((channel) -> channel.sendMessage(embed.getEmbed()).queue());

    }
}
