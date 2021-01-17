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
import io.paradaux.csbot.managers.PermissionManager;
import io.paradaux.csbot.managers.SMTPManager;
import io.paradaux.csbot.models.interal.ConfigurationEntry;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;

import javax.mail.MessagingException;

public class SendEmailCommand extends PrivilegedCommand {

    public SendEmailCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager) {
        super(config, logger, permissionManager);
        this.name = "sendemail";
        this.aliases = new String[]{"sem"};
        this.help = "Administrator utility to send email.";
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

        if (args.length < 3) {
            respondSyntaxError(message, ";sendemail <email address> <verification code>"
                    + " <@mention/userid>");
            return;
        }

        Member target = retrieveMember(message.getGuild(), parseTarget(message, 3, args));

        if (target == null) {
            message.getChannel().sendMessage("Invalid target specified.").queue();
            return;
        }

        try {
            SMTPManager.getInstance().sendVerificationEmail(args[0], args[1], args[2]);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
