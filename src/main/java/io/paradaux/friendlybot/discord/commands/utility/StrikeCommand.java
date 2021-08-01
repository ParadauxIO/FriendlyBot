package io.paradaux.friendlybot.discord.commands.utility;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.friendlybot.core.utils.StringUtils;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.BaseCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.Logger;

public class StrikeCommand extends BaseCommand {

    public StrikeCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "strike";
        this.help = "Generate unicode strikethrough text.";
    }

    @Override
    protected void execute(CommandEvent event) {

        Message message = event.getMessage();

        if (event.getArgs().isEmpty()) {
            respondSyntaxError(message, ";strike <message>");
            return;
        }

        MessageEmbed embed = new EmbedBuilder()
                .setColor(NumberUtils.randomColor())
                .setTitle("~~Strikethrough Generator~~")
                .setDescription("Unicode: `" + StringUtils.toStrikeOut(event.getArgs()) + "`\n"
                        + "Markdown: ~~" + event.getArgs() + "~~")
                .build();

        message.getChannel().sendMessage(embed).queue();
    }
}
