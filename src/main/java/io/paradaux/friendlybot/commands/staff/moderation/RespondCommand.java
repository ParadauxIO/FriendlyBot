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
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.database.ModMailEntry;
import io.paradaux.friendlybot.utils.models.database.ModMailResponse;
import io.paradaux.friendlybot.utils.models.types.PrivilegedCommand;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RespondCommand extends PrivilegedCommand {

    private final MongoManager mongo;
    public RespondCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager, MongoManager mongo) {
        super(config, logger, permissionManager);
        this.mongo = mongo;
        this.name = "respond";
        this.help = "Responds to a modmail message.";
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

        if (args.length < 2) {
            respondSyntaxError(message, ";respond <ticketnumber> <message>");
            return;
        }

        ModMailEntry entry = mongo.getModMailEntry(args[0]);

        if (entry == null) {
            message.addReaction("\uD83D\uDEAB").queue();
            message.getChannel().sendMessage("That ticket was not found").queue();
            return;
        }

        if (entry.getStatus() == ModMailEntry.ModMailStatus.CLOSED) {
            message.addReaction("\uD83D\uDEAB").queue();
            message.getChannel().sendMessage("You cannot respond to closed tickets.").queue();
            return;
        }

        StringBuilder responseContent = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            responseContent.append(args[i]).append(" ");
        }

        ModMailResponse response = new ModMailResponse()
                .setMessage(responseContent.toString())
                .setAuthorId(event.getAuthor().getId());

        List<ModMailResponse> existingResponses = entry.getResponses();

        if (existingResponses == null) {
            existingResponses = new ArrayList<>();
        }

        existingResponses.add(response);

        entry.setLastResponded(new Date())
                .setResponses(existingResponses);

        mongo.updateModMailEntry(entry.getTicketNumber(), entry);

        // Now that we've updated the record of the response, inform the ticket owner.




    }
}
