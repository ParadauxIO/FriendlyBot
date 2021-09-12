package io.paradaux.friendlybot.bot.commands.image;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.BaseCommand;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.EmbedBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Command(name = "", description = "", permission = "", aliases = {})
public class DogCommand extends BaseCommand {

    private static final String DOG_API = "https://dog.ceo/api/breeds/image/random";

    public DogCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "dog";
        this.aliases = new String[]{"pupper", "puppers"};
        this.help = "Woof, Woof!";
    }

    @Override
    protected void execute(CommandEvent event) {

        HttpApi http = new HttpApi(getLogger());
        HttpRequest request = http.plainRequest(DOG_API);

        http.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept((response) -> {
            EmbedBuilder builder = new EmbedBuilder()
                    .setImage(new JSONObject(response.body()).getString("message"))
                    .setColor(NumberUtils.randomColor())
                    .setFooter("For " + event.getAuthor().getName());

            event.getMessage().getChannel().sendMessage(builder.build()).queue();
        }).join();

    }

}
