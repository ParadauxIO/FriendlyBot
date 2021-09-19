package io.paradaux.friendlybot.bot.commands.joke;

import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.bot.command.CommandBody;
import io.paradaux.friendlybot.bot.command.DiscordCommand;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.friendlybot.core.utils.RandomUtils;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Command(name = "charmander", description = "Cute charmander pics!", permission = "command.charmander", aliases = {"charm"})
public class CharmanderCommand extends DiscordCommand {

    private static final String CHARMANDER_API = "https://cdn.paradaux.io/static/charmander/";
    private static final int CHARMANDER_COUNT = 38;
    private static final List<Integer> GIFS = new ArrayList<>();
    private final RandomUtils random = new RandomUtils();

    static {
        Collections.addAll(GIFS, 26);
    }

    @Override
    public void execute(FGuild guild, CommandBody body) {
        int id = random.pickRandomNumber(1, CHARMANDER_COUNT);

        EmbedBuilder builder = new EmbedBuilder()
                .setImage(CHARMANDER_API + id + (GIFS.contains(id) ? ".gif" : ".jpg"))
                .setColor(NumberUtils.randomColor())
                .setFooter("For " + body.getUser().getName() + " Charmander ID: " + id);

        body.getMessage().getChannel().sendMessageEmbeds(builder.build()).queue();
    }

}
