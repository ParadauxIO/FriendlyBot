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

package io.paradaux.csbot.commands.staff.technician;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.csbot.commands.staff.PrivilegedCommand;
import net.dv8tion.jda.api.entities.Message;

public class PermissionsCommand extends PrivilegedCommand {

    public PermissionsCommand() {
        this.name = "permissions";
        this.aliases = new String[]{"perms", "perm"};
        this.help = "Modifies user permissions";
    }

    @Override
    protected void execute(CommandEvent event) {
        String authorID = event.getAuthor().getId();
        if (isNotManagement(authorID)) return;

        Message message = event.getMessage();
        String[] args = event.getArgs().split(" ");

        if (!getPermissionController().isTechnician(authorID)) return;

        switch (args[0]) {
            case "give": {
                switch (args[2]) {
                    case "admin": {
                        String discordID;
                        if (message.getMentionedMembers().size() >= 1) {
                            discordID = message.getMentionedMembers().get(0).getId();
                        } else {
                            discordID = args[3];
                        }

                        getPermissionController().addAdmin(discordID);
                        message.getChannel().sendMessage(String.format("Given `%s` `Admin` permissions.", discordID)).queue();
                        break;
                    }

                    case "mod": {
                        String discordID;
                        if (message.getMentionedMembers().size() >= 1) {
                            discordID = message.getMentionedMembers().get(0).getId();
                        } else {
                            discordID = args[3];
                        }

                        getPermissionController().addMod(discordID);
                        message.getChannel().sendMessage(String.format("Given `%s` `Mod` permissions.", discordID)).queue();
                        break;
                    }

                    case "technician": {
                        String discordID;
                        if (message.getMentionedMembers().size() >= 1) {
                            discordID = message.getMentionedMembers().get(0).getId();
                        } else {
                            discordID = args[3];
                        }

                        getPermissionController().addTechnician(discordID);
                        message.getChannel().sendMessage(String.format("Given `%s` `Technician` permissions.", discordID)).queue();
                        break;
                    }
                }

                return;
            }

            case "remove": {
                switch (args[2]) {
                    case "admin": {
                        String discordID;
                        if (message.getMentionedMembers().size() >= 1) {
                            discordID = message.getMentionedMembers().get(0).getId();
                        } else {
                            discordID = args[3];
                        }

                        getPermissionController().removeAdmin(discordID);
                        message.getChannel().sendMessage(String.format("Removed `%s`'s `Admin` permissions.", discordID)).queue();
                        break;
                    }

                    case "mod": {
                        String discordID;
                        if (message.getMentionedMembers().size() >= 1) {
                            discordID = message.getMentionedMembers().get(0).getId();
                        } else {
                            discordID = args[3];
                        }

                        getPermissionController().removeMod(discordID);
                        message.getChannel().sendMessage(String.format("Removed `%s`'s `Mod` permissions.", discordID)).queue();
                        break;
                    }

                    case "technician": {
                        String discordID;
                        if (message.getMentionedMembers().size() >= 1) {
                            discordID = message.getMentionedMembers().get(0).getId();
                        } else {
                            discordID = args[3];
                        }

                        getPermissionController().removeTechnician(discordID);
                        message.getChannel().sendMessage(String.format("Removed `%s`'s `Technician` permissions.", discordID)).queue();
                        break;
                    }
                }

            }
        }
    }
}
