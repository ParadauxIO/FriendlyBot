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
import io.paradaux.csbot.EmbedColour;
import io.paradaux.csbot.controllers.ConfigurationController;
import io.paradaux.csbot.controllers.LogController;
import io.paradaux.csbot.controllers.PermissionController;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.slf4j.Logger;

public class CiteCommand extends Command {

    // Dependencies
    private static final ConfigurationCache configurationCache = ConfigurationController.getConfigurationCache();
    private static final Logger logger = LogController.getLogger();
    private static final PermissionController permissionController = PermissionController.INSTANCE;

    public CiteCommand() {
        this.name = "cite";
        this.aliases = new String[]{"c"};
        this.help = "Provides various administrator utilities";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();
        String[] args = event.getArgs().split(" ");

        TextChannel channel = message.getMentionedChannels().get(0);

        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(EmbedColour.MODERATION.getColour());

        switch (Integer.parseInt(args[1])) {
            case 1: {
                builder.addField("1.", "**You are always expected to show respect to all fellow students and Trinity Staff. **Any " +
                        "form of name-calling, drama-stirring and spreading of rumours will not be tolerated. Harassment and repeated targeted abuse " +
                        "of discord members is unacceptable and will be met with a permanent ban. ", true);
                break;
            }

            case 2: {
                builder.addField("2. ", "**Any issues regarding the server or its members must be brought up in private." +
                        "** If you have an issue with the server or one of its members report it to your class representative or by using" +
                        " the [#mod-mail](https://discord.com/channels/757903425311604786/773541543164117013/774420036920016916) channel. ", true);
                break;
            }

            case 3: {
                builder.addField("3. ", "**Political and religious discussion is prohibited.** It goes outside of the scope of this discord server," +
                        " which aims to provide academic support to our fellow students and to facilitate intercommunication in" +
                        " computer science generally. These topics only cause division and discourage new people from joining in the" +
                        " conversation.", true);
                break;
            }

            case 4: {
                builder.addField("4. ", "**Members may not use the discord to share pornography, gore and otherwise illicit content.**" +
                        " Furthermore, any discussion of related material is prohibited. This includes topics such as" +
                        " piracy/copyright infringement.", true);
                break;
            }

            case 5: {
                builder.addField("5. ", "**Discord members are expected to conduct themselves as if they were using an official Trinity " +
                        "College social medium.** As such, all rules and regulations subject to those apply here. ** Please see the footnote for" +
                        " more information. This includes condoning plagiarism.", true);
                break;
            }

            case 6: {
                builder.addField("6. ", "**This platform is hosted on discord, as such, the discord terms of service must always be followed.**", true);
                break;
            }

            default: {
                message.getChannel().sendMessage("You did specify a rule!").queue();
                return;
            }

        }
        message.getChannel().sendMessage("Citation sent.").queue();
        channel.sendMessage(builder.build()).queue();
    }
}
