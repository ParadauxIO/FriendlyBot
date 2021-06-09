package io.paradaux.friendlybot.commands.fun;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.utils.NumberUtils;
import io.paradaux.friendlybot.utils.RandomUtils;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.types.BaseCommand;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.EmbedBuilder;
import org.slf4j.Logger;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CharmanderCommand extends BaseCommand {

    private static final String CHARMANDER_API = "https://cdn.paradaux.io/static/charmander/";
    private static final List<Integer> gifs = new ArrayList<>();
    private final RandomUtils random;

    static {
        Collections.addAll(gifs, 26);
    }

    public CharmanderCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "charmander";
        this.aliases = new String[]{"charm"};
        this.help = "Cute charmander pics!";
        this.random = new RandomUtils();
    }

    @Override
    protected void execute(CommandEvent event) {
        int id = random.pickRandomNumber(1, 31);

        EmbedBuilder builder = new EmbedBuilder()
                .setImage(CHARMANDER_API + id + (gifs.contains(id) ? ".gif" : ".jpg"))
                .setColor(NumberUtils.randomColor())
                .setFooter("For " + event.getAuthor().getName() + " Charmander ID: " + id);

        event.getMessage().getChannel().sendMessage(builder.build()).queue();
    }

}
