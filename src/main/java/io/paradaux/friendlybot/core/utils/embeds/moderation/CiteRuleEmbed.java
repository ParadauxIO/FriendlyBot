/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.embeds.moderation.CiteRuleEmbed :  31/01/2021, 01:27
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

package io.paradaux.friendlybot.core.utils.embeds.moderation;

import io.paradaux.friendlybot.core.utils.models.enums.EmbedColour;
import io.paradaux.friendlybot.core.utils.models.interfaces.Embed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class CiteRuleEmbed implements Embed {

    final String header = "**As a member of this discord server, you are expected to have read"
            + " these rules in their entirety," + " and by continuing to make use of the discord"
            + " server you agree to follow these at all times.**";

    final String rule1 = "**You are always expected to show respect to all fellow students and "
            + "Trinity Staff.**" + " Any form of name-calling, drama-stirring and spreading of "
            + "rumours will not be tolerated. Harassment and repeated targeted abuse of discord "
            + "members is unacceptable and will be met with a permanent ban.";

    final String rule2 = "**Any issues regarding the server or its members must be brought up in"
            + " private. ** If you have an issue with the server or one of its members report "
            + "it to your " + "class representative or by using the [#mod-mail](https://discord.com/"
            + "channels/757903425311604786/773541543164117013/774420036920016916) channel. ";

    final String rule3 = "**Political and religious discussion is prohibited.** It goes outside "
            + "of the scope of this discord server, which aims to provide academic support to our"
            + " fellow students and to facilitate " + "intercommunication in computer science "
            + "generally. These topics only cause division" + " and discourage new people from "
            + "joining in the conversation.";

    final String rule4 = "**Members may not use the discord to share pornography, gore and "
            + "otherwise illicit content.**" + " Furthermore, any discussion of related material"
            + " is prohibited. This includes topics such as" + " piracy/copyright infringement.";

    final String rule5 = "**Discord members are expected to conduct themselves as if they were"
            + " using an official Trinity " + "College social medium.** As such, all rules and"
            + "regulations subject to those apply here. **Please see the footnote for"
            + " more information.** This includes condoning **plagiarism**.";

    final String rule6 = "**This platform is hosted on discord, as such, the discord terms of"
            + " service must always be followed.**";

    final String pleasenote = "The Rules set is subject to change at any time. Staff members"
            + " may act upon something which is not" + " explicitly listed, moderators are trusted"
            + " to act on their own discretion. If you have an issue with this, please use"
            + " the [#mod-mail](https://discord.com/channels/757903425311604786/77354154316411701"
            + "3/774420036920016916) feature or contact your class representative. ";

    final EmbedBuilder builder = new EmbedBuilder();

    public CiteRuleEmbed(String section) {
        builder.setColor(EmbedColour.INFO.getColour());

        switch (section) {

            case "header": {
                builder.addField("Heading :: ", header, false);
                break;
            }

            case "1": {
                builder.addField("Rule 1 ::", rule1, false);
                break;
            }

            case "2": {
                builder.addField("Rule 2 ::", rule2, false);
                break;
            }

            case "3": {
                builder.addField("Rule 3 ::", rule3, false);
                break;
            }

            case "4": {
                builder.addField("Rule 4 ::", rule4, false);
                break;
            }

            case "5": {
                builder.addField("Rule 5 ::", rule5, false);
                break;
            }

            case "6": {
                builder.addField("Rule 6 ::", rule6, false);
                break;
            }

            case "pleasenote": {
                builder.addField("Please Note ::", pleasenote, false);
                break;
            }

            default: {
                builder.addField("No Section found.", "", false);
            }
        }

    }

    @Override
    public void sendEmbed(TextChannel channel) {
        channel.sendMessage(builder.build()).queue();
    }
    public EmbedBuilder getBuilder() { return builder; }
}
