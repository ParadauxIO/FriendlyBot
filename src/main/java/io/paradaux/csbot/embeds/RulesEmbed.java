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

package io.paradaux.csbot.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class RulesEmbed implements IEmbedMessage {

    EmbedBuilder builder;

    public RulesEmbed() {
        this.builder = new EmbedBuilder();
        create();
    }

    public void create() {
        builder.setAuthor("The Computer Science Friendly Discord.");
        builder.setColor(EmbedColour.INFO.getColour());

        builder.addField("Discord Rules / Code of Conduct", "As a member of this discord server, you are expected to have read these rules in their " +
                "entirety, and by continuing to make use of the discord server you agree to follow these at all times.", false);

        builder.addField("1.", "You are expected to show respect to all fellow students and Trinity Staff at all times. Any form of name-calling," +
                " drama-stirring and falsely accusing people will not be tolerated. Harassment and repeated targeted abuse of discord members is unacceptable" +
                " and will be met with a permanent ban. ", true);

        builder.addField("2. ", "Political and religious discussion is prohibited. It goes outside of the scope of this discord server, which aims" +
                " to provide academic support to our fellow students, and to facilitate intercommunication in computer science generally. These topics only cause " +
                "division and discourage new people from joining in the conversation.", true);

        builder.addField("3. ", "Any form of drama, whether it started within the server or not must be continued in private. If you have an issue with" +
                " another member of the discord report it to your class representative or someone on the moderation team by using the mod-mail feature or via " +
                "direct-messaging (DM.) ", true);

        builder.addField("4. ", "Members may not use the discord to share pornography, gore and otherwise illicit content. Furthermore, any discussion of" +
                " related material is prohibited. This includes topics such as piracy/copyright infringement.", true);

        builder.addField("5. ", "Discord members are expected to conduct themselves as if they were using an official Trinity College social medium. As such, " +
                "all rules and regulations subject to those apply here. Please see the footnote for more information. This includes condoning plagiarism.", true);

        builder.addField("6. ", "This platform is hosted on discord, as such, the discord terms of service must be followed at all times.", true);

        builder.addField("Please note:", "The Rules set is subject to change at any time. Staff members may take action upon something which is not explicitly " +
                "listed, moderators are trusted to act on their own discretion. If you have an issue with this please use the mod-mail feature or contact a technician. ", false);

        builder.addField("Footnotes:", " [Trinity College: Ethics Policy](https://www.tcd.ie/about/policies/ethics-policy.php)\n" +
                "[Trinity College: Dignity and Respect Policy](https://www.tcd.ie/equality/policy/dignity-respect-policy/)\n" +
                "[Discord: Terms of Service](https://discord.com/terms)", false);

        builder.setFooter("Computer Science Friendly Bot | v0.1 | Rules Last Edited: 4/11/2020");
    }

    @Override
    public MessageEmbed build() {
        return builder.build();
    }

}
