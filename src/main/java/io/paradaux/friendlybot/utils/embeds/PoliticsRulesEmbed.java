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

package io.paradaux.friendlybot.utils.embeds;

import io.paradaux.friendlybot.utils.EmbedColour;
import io.paradaux.friendlybot.utils.models.interfaces.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class PoliticsRulesEmbed implements Embed {

    final EmbedBuilder builder = new EmbedBuilder();

    public PoliticsRulesEmbed() {
        builder.setAuthor("The Computer Science Friendly Discord | Rules: Politics Edition");
        builder.setColor(EmbedColour.INFO.getColour());

        builder.addField("Discord Rules / Code of Conduct", "", false);

        builder.addField("Rule 1. ::", "**All of #rules except for rule 3 apply here.**",
                true);

        builder.addField("Rule 2. ::", "**Conversations must remain civil** attack the"
                + " idea, not the person.", true);

        builder.addField("Rule 3. ::", "**Conversations should aim to be constructive**"
                + " This includes allowing others to come into the conversation and contribute "
                + "their beliefs.", true);

        builder.addField("Rule 4. ::", "You must conduct yourself in accordance with the"
                + " **Trinity College Dignity and Respect Policy** linked below.", true);

        builder.addField("Rule 5. ::", "**This platform is hosted on discord, as such,"
                + " the discord terms of service must always be followed.**", true);

        builder.addField("Please note:", "The Politics Rules set is subject to change"
                + " at any time. Staff members may act upon something which is not"
                + " explicitly listed, moderators are trusted to act on their own discretion. If "
                + "you have an issue with this, please use  the [#mod-mail](https://discord"
                + ".com/channels/757903425311604786/773541543164117013/774420036920016916) feature"
                + " or contact your class representative. ", false);

        builder.addField("Footnotes:", " [Trinity College: Ethics Policy](https://www."
                + "tcd.ie/about/policies/ethics-policy.php)\n"
                + "[Trinity College: Dignity and Respect Policy](https://www.tcd.ie/equality/policy"
                + "/dignity-respect-policy/)\n"
                + "[Discord: Terms of Service](https://discord.com/terms)", false);

        builder.setFooter("Computer Science Friendly Bot | v0.1 | Politics Rules Last Edited: "
                + "20/11/2020");
    }

    @Override
    public void sendEmbed(TextChannel channel) {
        channel.sendMessage(builder.build()).queue();
    }
}
