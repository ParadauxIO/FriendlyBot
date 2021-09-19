/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.staff.moderation.PruneCommand :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.bot.commands.staff.admin;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.PrivilegedCommand;
import io.paradaux.friendlybot.managers.PermissionManager;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;

@Command(name = "", description = "", permission = "", aliases = {})
public class PruneCommand extends PrivilegedCommand {

    public PruneCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager) {
        super(config, logger, permissionManager);
        this.name = "prune";
        this.aliases = new String[]{"clear"};
        this.help = "Prunes the specified amount of messages in the current channel.";
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

        if (args.length == 0) {
            respondSyntaxError(message, ";prune <amount>");
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException ok) {
            respondSyntaxError(message, ";prune <amount>");
            return;
        }

        message.getChannel().getHistory().retrievePast(amount).queue((history) -> {
            history.forEach((historicMessage -> historicMessage.delete().queue()));
            message.getChannel().sendMessage("Pruned " + amount + " messages.").queue();
        });
    }
}
