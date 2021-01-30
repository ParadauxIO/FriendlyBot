/*
 * Copyright (c) 2021 |  Rían Errity. GPLv3
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

package io.paradaux.friendlybot.commands.fun;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.utils.WebApiUtil;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.enums.EmbedColour;
import io.paradaux.friendlybot.utils.models.enums.WebSettings;
import io.paradaux.friendlybot.utils.models.types.BaseCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URL;

public class InspireCommand extends BaseCommand {

    private static final String INSPIRE_API = "https://inspirobot.me/api?generate=true";

    public InspireCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "inspire";
        this.aliases = new String[]{"inspireme", "im"};
        this.help = "Sends some heartwarming inspirational quotes!.";
    }

    @Override
    protected void execute(CommandEvent event) {
        try {
            String content = WebApiUtil.getStringWithThrows(new URL(INSPIRE_API), "GET",
                    WebSettings.WEB_TIMEOUT, WebSettings.WEB_USER_AGENT, WebSettings.getTextWebHeaders());

            EmbedBuilder embed = new EmbedBuilder();
            embed.setImage(content);
            embed.setColor(EmbedColour.NEUTRAL.getColour());
            embed.setFooter("For " + event.getAuthor().getName());

            event.getChannel().sendMessage(embed.build()).queue();
        } catch (IOException e) {
            event.getChannel().sendMessage("An error occurred.").queue();
        }

    }
}
