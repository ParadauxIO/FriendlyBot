/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.staff.technician.PermissionsCommand :  31/01/2021, 01:26
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
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;

public class PermissionsCommand extends PrivilegedCommand {

    public PermissionsCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager) {
        super(config, logger, permissionManager);
        this.name = "permissions";
        this.aliases = new String[]{"perms", "perm"};
        this.help = "Modifies user permissions";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();

        String[] args = getArgs(event.getArgs());
        String authorID = event.getAuthor().getId();

        PermissionManager manager = PermissionManager.getInstance();

        if (!manager.isTechnician(authorID)) {
            respondNoPermission(message, "[Technician]");
            return;
        }

        if (args.length < 3) {
            respondSyntaxError(message, ";permissions <give/remove> <mod/admin/"
                    + "technician> <@mention/discordID>");
            return;
        }

        Member target = retrieveMember(message.getGuild(), parseTarget(message, args[3]));

        if (target == null) {
            message.getChannel().sendMessage("Invalid target specified.").queue();
            return;
        }

        switch (args[0]) {
            case "give": {

                switch (args[1]) {
                    case "mod": {
                        manager.addMod(target.getId());
                        break;
                    }

                    case "admin": {
                        manager.addAdmin(authorID);
                        break;
                    }

                    case "technician": {
                        manager.addTechnician(authorID);
                        break;
                    }

                    default: {
                        respondSyntaxError(message, ";permissions <give/remove> <mod/admin/"
                                + "technician> <@mention/discordID>");
                        return;
                    }
                }

                String responseMessage = String.format("Given `%s` `%s` permissions.",
                        target.getUser().getAsTag(), args[1]);
                message.getChannel().sendMessage(responseMessage).queue();
                break;
            }

            case "remove": {

                switch (args[1]) {

                    case "mod": {
                        manager.removeMod(authorID);
                        break;
                    }

                    case "admin": {
                        manager.removeAdmin(authorID);
                        break;
                    }

                    case "technician": {
                        manager.removeTechnician(authorID);
                        break;
                    }

                    default: {
                        respondSyntaxError(message, ";permissions <give/remove> <mod/admin/"
                                + "technician> <@mention/discordID>");
                        return;
                    }
                }

                String responseMessage = String.format("Taken `%s`'s `%s` permissions.",
                        target.getUser().getAsTag(), args[1]);
                message.getChannel().sendMessage(responseMessage).queue();
                break;
            }

            default: {
                respondSyntaxError(message, ";permissions <give/remove> <mod/admin/"
                        + "technician> <@mention/discordID>");
                break;
            }
        }
    }
}
