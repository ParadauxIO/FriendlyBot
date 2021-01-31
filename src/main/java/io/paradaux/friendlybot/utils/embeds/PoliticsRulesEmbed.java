/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.embeds.PoliticsRulesEmbed :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.utils.embeds;

import io.paradaux.friendlybot.utils.models.enums.EmbedColour;
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
    public EmbedBuilder getBuilder() { return builder; }
}
