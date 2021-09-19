/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.utility.ServerInfoCommand :  03/02/2021, 05:42
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

package io.paradaux.friendlybot.bot.commands.util;

import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.bot.command.CommandBody;
import io.paradaux.friendlybot.bot.command.DiscordCommand;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;

@Command(name = "serverinfo", description = "Provides information about the server", permission = "commands.serverinfo", aliases = {"si", "srvinfo"})
public class ServerInfoCommand extends DiscordCommand {

    @Override
    public void execute(FGuild guild, CommandBody body) {
        Message message = body.getMessage();


        List<Emote> emotes = guild.getGuild().getEmotes();
        StringBuilder builder = new StringBuilder();

        for (final var emote : emotes) {
            builder.append(":" + emote.getName() + ":");
        }

        MessageEmbed embed = new EmbedBuilder()
                .setTitle(guild.getGuild().getName() + " » Server Information")
                .setColor(0x009999)
                .setThumbnail(guild.getGuild().getIconUrl())
                .addField("Owner", retrieveMember(guild.getGuild(), guild.getGuild().getOwnerId()).getUser().getAsTag(), true)
                .addField("Member Count", String.valueOf(guild.getGuild().getMemberCount()), true)
                .build();

        message.getChannel().sendMessageEmbeds(embed).queue();
    }
}
