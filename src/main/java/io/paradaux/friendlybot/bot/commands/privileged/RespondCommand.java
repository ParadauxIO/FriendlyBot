/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.staff.moderation.RespondCommand :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.bot.commands.privileged;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.database.ModMailEntry;
import io.paradaux.friendlybot.core.utils.models.database.ModMailResponse;
import io.paradaux.friendlybot.core.utils.models.enums.TicketStatus;
import io.paradaux.friendlybot.core.utils.models.types.PrivilegedCommand;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.managers.PermissionManager;
import net.dv8tion.jda.api.entities.Member;
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

        if (!isStaff(event.getGuild(), authorID)) {
            respondNoPermission(message, "[Moderator, Administrator]");
            return;
        }

        if (args.length < 2) {
            respondSyntaxError(message, ";respond <ticketnumber> <message>");
            return;
        }

        ModMailEntry entry = mongo.getModMailEntry(args[0]);

        if (entry == null) {
            message.addReaction("🚫").queue();
            message.getChannel().sendMessage("That ticket was not found").queue();
            return;
        }

        if (entry.getStatus() == TicketStatus.CLOSED) {
            message.addReaction("🚫").queue();
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

        Member ticketHolder = retrieveMember(message.getGuild(), entry.getUserID());

        if (ticketHolder == null) {
            message.getChannel().sendMessage("Error while notifying the user of the ticket response. Are they still in the discord?").queue();
            return;
        }

        ticketHolder.getUser().openPrivateChannel().queue((privateChannel -> {
            privateChannel.sendMessage("There has been a new response to your mod-mail query.\n`" + response.getMessage() + "`\n"
                    + "In order to add on to your ticket, just message me here.").queue();
        }));

        message.getChannel().sendMessage("Response: `" + response.getMessage() + "` sent to " + ticketHolder.getEffectiveName()).queue();
    }
}
