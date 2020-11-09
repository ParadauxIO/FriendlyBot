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

package io.paradaux.csbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.csbot.ConfigurationCache;
import io.paradaux.csbot.controllers.*;
import io.paradaux.csbot.embeds.*;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;

import javax.mail.MessagingException;

/**
 * This is a command which
 *
 * @author Rían Errity
 * @version Last modified for 0.1.0-SNAPSHOT
 * @since 4/11/2020 DD/MM/YY
 * @see io.paradaux.csbot.CSBot
 * */

public class AdminCommand extends Command {

    // Dependencies
    private static final ConfigurationCache configurationCache = ConfigurationController.getConfigurationCache();
    private static final Logger logger = LogController.getLogger();
    private static final PermissionController permissionController = PermissionController.INSTANCE;

    public AdminCommand() {
        this.name = "admin";
        this.aliases = new String[]{"a", "adm", "administrator"};
        this.help = "Provides various administrator utilities";
    }

    @Override
    protected void execute(CommandEvent event) {
        String authorID = event.getAuthor().getId();
        if (!(permissionController.isAdmin(authorID) || permissionController.isTechnician(authorID))) return;

        Message message = event.getMessage();
        String[] args = event.getArgs().split(" "); // Space

        boolean a = true;
        message.delete().queue();
        switch (args[0]) {

            case "sendembed": {

                switch (args[1]) {

                    case "rules": {
                        new RulesEmbed().sendEmbed(event.getChannel(), null);
                        break;
                    }

                    case "modmail": {
                        new ModMailEmbed().sendEmbed(event.getChannel(), null);
                        break;
                    }

                    case "pickcourse": {
                        new PickYourCourseEmbed().sendEmbed(event.getChannel(), null);
                        break;
                    }

                    case "verification": {
                        new VerificationEmbed().sendEmbed(event.getChannel(), null);
                        break;
                    }

                    case "pickpronouns": {
                        new PickYourPronounsEmbed().sendEmbed(event.getChannel(), null);
                        break;
                    }

                    case "pickyear": {
                        new PickYourYearEmbed().sendEmbed(event.getChannel(), null);
                        break;
                    }

                    case "pickclass": {
                        new PickYourClassEmbed().sendEmbed(event.getChannel(), null);
                        break;
                    }

                    case "pickinterests": {
                        new PickYourInterestsEmbed().sendEmbed(event.getChannel(), null);
                        break;
                    }

                    case "roleselection": {
                        new PickYourCourseEmbed().sendEmbed(event.getChannel(), null);
                        new PickYourYearEmbed().sendEmbed(event.getChannel(), null);
                        new PickYourPronounsEmbed().sendEmbed(event.getChannel(), null);
                        new PickYourClassEmbed().sendEmbed(event.getChannel(), null);
                        new PickYourInterestsEmbed().sendEmbed(event.getChannel(), null);
                        break;
                    }
                }

            }

            case "sendemail": {
                if (args.length > 3) {
                    event.reply("You need to supply both an email and a verification code.");
                }

                try {
                    EmailController.INSTANCE.sendVerificationEmail(args[1], args[2], args[3]);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

            }

            case "permissions": {
                if(!permissionController.isTechnician(authorID)) return;
                switch (args[1]) {
                    case "give": {
                        switch (args[2]) {
                            case "admin": {
                                String discordID;
                                if (message.getMentionedMembers().size() >= 1) {
                                    discordID = message.getMentionedMembers().get(0).getId();
                                } else {
                                    discordID = args[3];
                                }

                                permissionController.addAdmin(discordID);
                                message.getChannel().sendMessage(String.format("Given `%s` `Admin` permissions.", discordID)).queue();

                            }

                            case "mod": {
                                String discordID;
                                if (message.getMentionedMembers().size() >= 1) {
                                    discordID = message.getMentionedMembers().get(0).getId();
                                } else {
                                    discordID = args[3];
                                }

                                permissionController.addMod(discordID);
                                message.getChannel().sendMessage(String.format("Given `%s` `Mod` permissions.", discordID)).queue();
                            }

                            case "technician": {
                                String discordID;
                                if (message.getMentionedMembers().size() >= 1) {
                                    discordID = message.getMentionedMembers().get(0).getId();
                                } else {
                                    discordID = args[3];
                                }

                                permissionController.addTechnician(discordID);
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

                                permissionController.removeAdmin(discordID);
                                message.getChannel().sendMessage(String.format("Removed `%s`'s `Admin` permissions.", discordID)).queue();
                            }

                            case "mod": {
                                String discordID;
                                if (message.getMentionedMembers().size() >= 1) {
                                    discordID = message.getMentionedMembers().get(0).getId();
                                } else {
                                    discordID = args[3];
                                }

                                permissionController.removeMod(discordID);
                                message.getChannel().sendMessage(String.format("Removed `%s`'s `Mod` permissions.", discordID)).queue();
                            }

                            case "technician": {
                                String discordID;
                                if (message.getMentionedMembers().size() >= 1) {
                                    discordID = message.getMentionedMembers().get(0).getId();
                                } else {
                                    discordID = args[3];
                                }

                                permissionController.removeTechnician(discordID);
                                message.getChannel().sendMessage(String.format("Removed `%s`'s `Technician` permissions.", discordID)).queue();
                            }
                        }

                    }
                }
            }

        }

    }


}
