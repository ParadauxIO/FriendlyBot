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

package io.paradaux.friendlybot.commands.utility;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.types.BaseCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.Logger;

import java.util.List;

public class ServerInfoCommand extends BaseCommand {

    public ServerInfoCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "serverinfo";
        this.help = "Provides information about the server.";
        this.aliases = new String[]{"si, srvinfo"};
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();
        Guild guild = event.getGuild();

        List<Emote> emotes = guild.getEmotes();
        StringBuilder builder = new StringBuilder();

        for (final var emote : emotes) {
            builder.append(":" + emote.getName() + ":");
        }

        MessageEmbed embed = new EmbedBuilder()
                .setTitle(guild.getName() + " » Server Information")
                .setColor(0x009999)
                .setThumbnail(guild.getIconUrl())
                .addField("Owner", retrieveMember(guild, guild.getOwnerId()).getUser().getAsTag(), true)
                .addField("Member Count", String.valueOf(guild.getMemberCount()), true)
//                .addField("Emojis", emotes.size() + ": " + builder.toString(), false)
                .build();

        message.getChannel().sendMessage(embed).queue();
    }
}
