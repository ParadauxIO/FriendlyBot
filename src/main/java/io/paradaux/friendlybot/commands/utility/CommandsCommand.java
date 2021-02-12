/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.utility.CommandsCommand :  06/02/2021, 11:16
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
import io.paradaux.friendlybot.utils.TimeUtils;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.types.BaseCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.stream.Collectors;

public class CommandsCommand extends BaseCommand {

    private static final String THUMBNAIL_IMAGE = "https://cdn.paradaux.io/img/ybv70.png";
    private static final String THIS_EMOJI = "<a:thisicon:807586522181533707>";
    private final String funCommandContent;
    private final String utilityCommandContent;

    public CommandsCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "commands";
        this.help = "Command Information Embed";

        InputStream inputStream = getClass().getResourceAsStream("/data/funcommands.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        this.funCommandContent = reader.lines().collect(Collectors.joining("\n"));

        inputStream = getClass().getResourceAsStream("/data/utilitycommands.txt");
        reader = new BufferedReader(new InputStreamReader(inputStream));
        this.utilityCommandContent = reader.lines().collect(Collectors.joining("\n"));
    }

    @Override
    protected void execute(CommandEvent event) {
        event.getMessage().delete().queue();
        
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("FriendlyBot » Commands")
                .setColor(0x009999)
                .setThumbnail(THUMBNAIL_IMAGE)
                .addField(THIS_EMOJI + " __Fun Commands__", funCommandContent, false)
                .addField(THIS_EMOJI + " __Utility Commands__", utilityCommandContent, false)
                .setFooter("Last updated: " + TimeUtils.formatTime(new Date()), "https://cdn.paradaux.io/img/fteuv.png")
                .build();

        event.getChannel().sendMessage(embed).queue();
    }
}
