/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.utility.TagCommand :  06/02/2021, 17:26
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

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.friendlybot.core.utils.TimeUtils;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.database.TagEntry;
import io.paradaux.friendlybot.core.utils.models.types.PrivilegedCommand;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.managers.TagManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.Logger;

import java.util.Date;
import java.util.Locale;

@Command(name = "", description = "", permission = "", aliases = {})
public class TagCommand extends PrivilegedCommand {

    private final MongoManager mongo;

    public TagCommand(ConfigurationEntry config, Logger logger, MongoManager mongo) {
        super(config, logger);
        this.mongo = mongo;
        this.name = "tag";
        this.help = "Manage tags. privileged";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();
        String[] args = getArgs(event.getArgs());

        if (args.length == 0) {
            respondSyntaxError(message, ";tag <create/delete/view/list> [id] [content]");
            return;
        }

        TagManager tags = TagManager.getInstance();

        switch (args[0]) {
            case "create": {
                TagEntry entry = tags.getTagById(event.getGuild().getId(), args[1].toLowerCase(Locale.ROOT));

                if (entry != null) {
                    MessageEmbed embed = new EmbedBuilder()
                            .setColor(0xeb5132)
                            .setTitle("This tag already exists")
                            .setDescription("This tag is owned by " + retrieveMember(event.getGuild(), entry.getDiscordId()))
                            .build();
                    message.getChannel().sendMessage(embed).queue();
                }

                // Tag doesn't exist, make a new one.
                entry = new TagEntry()
                        .setId(args[1].toLowerCase(Locale.ROOT))
                        .setContent(parseSentance(2, args))
                        .setDiscordId(event.getAuthor().getId())
                        .setGuildId(event.getGuild().getId())
                        .setTimeCreated(new Date());

                tags.addTag(entry);

                MessageEmbed embed = new EmbedBuilder()
                        .setTitle("Tag successfully created.")
                        .setColor(0x00cc99)
                        .build();

                event.getChannel().sendMessage(embed).queue();
                break;
            }

            case "delete": {
                TagEntry entry = tags.getTagById(event.getGuild().getId(), args[1]);
                if (entry == null) {
                    MessageEmbed embed = new EmbedBuilder()
                            .setColor(0xeb5132)
                            .setTitle("This tag does not exist.")
                            .build();
                    message.getChannel().sendMessage(embed).queue();
                    return;
                }

                if (!(entry.getDiscordId().equals(event.getAuthor().getId()) || isStaff(event.getGuild(), event.getAuthor().getId()))) {
                    // Not staff, not the owner.
                    MessageEmbed embed = new EmbedBuilder()
                            .setColor(0xeb5132)
                            .setTitle("You do not have permission to modify this tag.")
                            .setDescription("This tag is owned by " + retrieveMember(event.getGuild(), entry.getDiscordId()))
                            .build();
                    message.getChannel().sendMessage(embed).queue();
                    return;
                }

                tags.removeTag(entry);

                MessageEmbed embed = new EmbedBuilder()
                        .setTitle("Tag successfully removed.")
                        .setColor(0x00cc99)
                        .build();

                message.getChannel().sendMessage(embed).queue();
                break;
            }

            case "view": {
                TagEntry entry = tags.getTagById(event.getGuild().getId(), args[1].toLowerCase(Locale.ROOT));

                if (entry == null) {
                    MessageEmbed embed = new EmbedBuilder()
                            .setColor(0xeb5132)
                            .setTitle("This tag does not exist.")
                            .build();
                    message.getChannel().sendMessage(embed).queue();
                    return;
                }

                Member owner = retrieveMember(event.getGuild(), entry.getDiscordId());
                String tag = owner != null ? owner.getUser().getAsTag() : "User no longer in guild.";

                MessageEmbed embed = new EmbedBuilder()
                        .setTitle("Tag » " + entry.getId())
                        .setColor(NumberUtils.randomColor())
                        .addField("Owner", tag, true)
                        .addField("Created", TimeUtils.formatTime(entry.getTimeCreated()), true)
                        .build();

                message.getChannel().sendMessage(embed).queue();
                break;
            }

            default: {
                respondSyntaxError(message, ";tag <create/delete/view/list> [id] [content]");
            }
        }
    }
}
