package io.paradaux.friendlybot.discord.commands.utility;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.friendlybot.core.utils.models.types.BaseCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import org.slf4j.Logger;

public class TagsCommand extends BaseCommand {

    private static final String TAGS_URL = "https://paradaux.io/projects/friendlybot/tools/tag_viewer.html?serverid=%s";

    public TagsCommand(Logger logger) {
        super(logger);
        this.name = "tags";
        this.help = "Sends a link to the tag web viewer, so you can see all of the tags currently set in the given guild.";
    }

    @Override
    protected void execute(CommandEvent event) {
        event.getChannel().sendMessage(new EmbedBuilder()
                .setColor(NumberUtils.randomColor()).setDescription("**You can view all tags for this guild here**: \n"
                        + String.format(TAGS_URL, event.getGuild().getId())).build()).queue();
    }
}
