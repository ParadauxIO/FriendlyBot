/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.staff.technician.DmCommand :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.commands.staff.technician;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.types.PrivilegedCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;

public class DmCommand extends PrivilegedCommand {

    public DmCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager) {
        super(config, logger, permissionManager);
        this.name = "dm";
        this.aliases = new String[]{"pm"};
        this.help = "Private messages the specified user the specified message";
    }

    @Override
    protected void execute(CommandEvent event) {
        // TODO Command stub

        if (!isStaff(event.getGuild(), event.getAuthor().getId())) {
            respondNoPermission(event.getMessage(), "Moderator");
            return;
        }

        String[] args = getArgs(event.getArgs());

        if (args.length < 3) {
            respondSyntaxError(event.getMessage(), ";dm <user> <message>");
            return;
        }

        User target = parseTarget(event.getMessage(), args[0]);

        if (target == null) {
            respondSyntaxError(event.getMessage(), ";dm <user> <message>");
            return;
        }

        target.openPrivateChannel().queue((channel) -> {
            String message = parseSentance(1, args);
            channel.sendMessage(message).queue();
            event.getChannel().sendMessage("Sent `" + message + "` to target: " + target.getAsTag()).queue();
        });

    }

}
