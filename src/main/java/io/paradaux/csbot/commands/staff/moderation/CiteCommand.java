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

package io.paradaux.csbot.commands.staff.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.csbot.models.interal.ConfigurationEntry;
import io.paradaux.csbot.controllers.ConfigurationController;
import io.paradaux.csbot.controllers.LogController;
import io.paradaux.csbot.controllers.PermissionController;
import io.paradaux.csbot.embeds.Embed;
import io.paradaux.csbot.embeds.moderation.CiteRuleEmbed;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.slf4j.Logger;

public class CiteCommand extends Command {

    // Dependencies
    private static final ConfigurationEntry configurationEntry = ConfigurationController.getConfigurationEntry();
    private static final Logger logger = LogController.getLogger();
    private static final PermissionController permissionController = PermissionController.INSTANCE;

    public CiteCommand() {
        this.name = "cite";
        this.help = "Cites a certain rule to remind users of the rules which we have in place.";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();
        String[] args = event.getArgs().split(" ");

        TextChannel channel = message.getMentionedChannels().get(0);

        Embed CiteRuleEmbed = new CiteRuleEmbed(args[0]);

        message.getChannel().sendMessage("Citation sent.").queue();
        CiteRuleEmbed.sendEmbed(channel);
    }
}