package io.paradaux.friendlybot.bot.commands.util;

import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.bot.command.CommandBody;
import io.paradaux.friendlybot.bot.command.DiscordCommand;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import org.slf4j.Logger;

@Command(name = "", description = "", permission = "", aliases = {})
public class TagsCommand extends DiscordCommand {

    private static final String TAGS_URL = "https://paradaux.io/projects/friendlybot/tools/tag_viewer.html?serverid=%s";

    public TagsCommand(Logger logger) {
        super(logger);
        this.name = "tags";
        this.help = "Sends a link to the tag web viewer, so you can see all of the tags currently set in the given guild.";
    }

    @Override
    public void execute(FGuild guild, CommandBody body) {
        event.getChannel().sendMessage(new EmbedBuilder()
                .setColor(NumberUtils.randomColor()).setDescription("**You can view all tags for this guild here**: \n"
                        + String.format(TAGS_URL, event.getGuild().getId())).build()).queue();
    }
}
