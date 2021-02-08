/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.staff.moderation.TicketCommand :  31/01/2021, 01:26
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
import io.paradaux.friendlybot.managers.DiscordBotManager;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.utils.StringUtils;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.database.ModMailEntry;
import io.paradaux.friendlybot.utils.models.database.ModMailResponse;
import io.paradaux.friendlybot.utils.models.enums.EmbedColour;
import io.paradaux.friendlybot.utils.models.enums.TicketStatus;
import io.paradaux.friendlybot.utils.models.types.PrivilegedCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;

import java.util.List;

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

        if (args.length == 0) {
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
                mongo.setModMailStatus(ticketNumber, TicketStatus.CLOSED);

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
                        .addField("Issue", entry.getIssue(), false)
                        .addBlankField(true)
                        .addBlankField(true);

                List<ModMailResponse> responses = entry.getResponses();

                if (responses == null) {
                    event.getChannel().sendMessage(embedBuilder.build()).queue();
                    break;
                }

                embedBuilder.addField("Responses:", "", false);

                for (ModMailResponse response : responses) {
                    User author = DiscordBotManager.getInstance().getUser(response.getAuthorId());
                    embedBuilder.addField(author.getName(), response.getMessage(), false);
                }

                event.getChannel().sendMessage(embedBuilder.build()).queue();
                break;
            }

            case "open": {
                ticketNumber = args[1];
                ModMailEntry entry = mongo.getModMailEntry(ticketNumber);

                Member member = retrieveMember(message.getGuild(), entry.getUserID());

                message.getChannel().sendMessage("Ticket involving: `" + entry.getIssue() + "` has been re-opened.").queue();
                mongo.setModMailStatus(ticketNumber, TicketStatus.OPEN);

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

            case "pending": {
                StringBuilder builder = new StringBuilder();

                for (final var entry : mongo.getModMailEntries(TicketStatus.OPEN)) {
                    builder.append(entry.getTicketNumber()).append(" ");
                }

                MessageEmbed embed = new EmbedBuilder()
                        .setTitle("Open Tickets")
                        .setColor(0x009999)
                        .setDescription("`" + builder.toString().trim() + "`")
                        .build();

                message.getChannel().sendMessage(embed).queue();
                break;
            }

            default: {
                respondSyntaxError(message, ";ticket <close/open/view> [ticketnumber]");
            }
        }
    }
}
