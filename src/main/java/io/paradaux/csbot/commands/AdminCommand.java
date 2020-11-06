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
import io.paradaux.csbot.controllers.EmailController;
import io.paradaux.csbot.embeds.RulesEmbed;
import net.dv8tion.jda.api.entities.Message;

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

    public AdminCommand() {
        this.name = "admin";
        this.aliases = new String[]{"a", "adm", "administrator"};
        this.help = "Provides various administrator utilities";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();
        String[] args = event.getArgs().split(" "); // Space
        switch (args[0]) {

            case "sendembed": {
                if (args[1].equalsIgnoreCase("rules")) {
                    new RulesEmbed().sendEmbed(event.getChannel(), null);
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

        }

    }


}
