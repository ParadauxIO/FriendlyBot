/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.staff.technician.VerificationCommand :  31/01/2021, 01:26
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
import io.paradaux.friendlybot.core.utils.models.exceptions.VerificationException;
import io.paradaux.friendlybot.core.utils.models.types.PrivilegedCommand;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.managers.VerificationManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;

public class VerificationCommand extends PrivilegedCommand {

    public VerificationCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager) {
        super(config, logger, permissionManager);
        this.name = "verification";
        this.help = "Administrator utility to manage verifications.";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();

        String[] args = getArgs(event.getArgs());
        String authorID = event.getAuthor().getId();

        if (!getPermissionManager().isTechnician(authorID)) {
            respondNoPermission(message, "[Technician]");
            return;
        }

        if (args.length == 0) {
            respondSyntaxError(message, ";verification <add/set/ispending/getpending/reset>>"
                    + " [@mention/userid] [email]");
            return;
        }

        VerificationManager verification = VerificationManager.getInstance();

        switch (args[0]) {
            case "add": {
                User target = parseTarget(message, args[1]);

                if (target == null) {
                    message.getChannel().sendMessage("Invalid target specified.").queue();
                    return;
                }

                try {
                    verification.setPendingVerification(args[1],
                            target.getId(), message.getGuild().getId());
                } catch (VerificationException exception) {
                    message.getChannel().sendMessage("An error occured: " + exception
                            .getMessage()).queue();
                }

                break;
            }

            case "set": {
                User target = parseTarget(message, args[1]);

                if (target == null) {
                    message.getChannel().sendMessage("Invalid target specified.").queue();
                    return;
                }

                try {
                    verification.setVerified(target.getId(),
                            message.getGuild().getId(), null);
                } catch (VerificationException exception) {
                    message.getChannel().sendMessage("An error occurred: " + exception
                            .getMessage()).queue();
                }

                break;
            }

            case "ispending": {
                User target = parseTarget(message, args[1]);

                if (target == null) {
                    message.getChannel().sendMessage("Invalid target specified.").queue();
                    return;
                }

                String not = (MongoManager.getInstance().isPendingVerification(target.getId())) ? "" : "not ";

                message.getChannel().sendMessage(target.getAsTag() + " is " + not + "pending verification.").queue();


                break;
            }

            case "getpendingusers": {
                message.getChannel().sendMessage("```" + verification.getPendingUsers() + "```").queue();
                break;
            }

            case "reset": {
                message.getChannel().sendMessage("Not yet implemented.").queue();
                break;
            }

            default: {
                respondSyntaxError(message, ";verification <add/ispending/getpending/reset>> [@mention/userid] [email]");
            }
        }
    }

}
