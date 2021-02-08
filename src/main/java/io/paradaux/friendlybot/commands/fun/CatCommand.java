package io.paradaux.friendlybot.commands.fun;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.utils.NumberUtils;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.types.BaseCommand;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.EmbedBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CatCommand extends BaseCommand {

    private static final String CAT_API = "https://api.thecatapi.com/v1/images/search";

    public CatCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "cat";
        this.aliases = new String[]{"meow"};
        this.help = "Add a little more meow to your life!";
    }

    @Override
    protected void execute(CommandEvent event) {

        HttpApi http = new HttpApi(getLogger());
        HttpRequest request = http.plainRequest(CAT_API);

        http.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept((response) -> {
            EmbedBuilder builder = new EmbedBuilder()
                    .setImage(new JSONArray(response.body()).getJSONObject(0).getString("url"))
                    .setColor(NumberUtils.randomColor())
                    .setFooter("For " + event.getAuthor().getName());

            event.getMessage().getChannel().sendMessage(builder.build()).queue();
        }).join();

    }
}
