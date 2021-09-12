package io.paradaux.friendlybot.bot.commands.image;

import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.bot.command.CommandBody;
import io.paradaux.friendlybot.bot.command.DiscordCommand;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.EmbedBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Command(name = "dog", description = "Get yourself a cute doggo pic!", permission = "command.dog", aliases = {"pupper",
        "puppers", "pup", "doggo", "doggos"})
public class DogCommand extends DiscordCommand {

    private static final String DOG_API = "https://dog.ceo/api/breeds/image/random";

    @Override
    public void execute(FGuild guild, CommandBody body) {

        HttpApi http = new HttpApi(getLogger());
        HttpRequest request = http.plainRequest(DOG_API);

        http.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept((response) -> {
            EmbedBuilder builder = new EmbedBuilder()
                    .setImage(new JSONObject(response.body()).getString("message"))
                    .setColor(NumberUtils.randomColor())
                    .setFooter("For " + body.getUser().getName());

            body.getMessage().getChannel().sendMessageEmbeds(builder.build()).queue();
        }).join();

    }

}
