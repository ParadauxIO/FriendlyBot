package io.paradaux.friendlybot.bot.commands;

import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.bot.command.CommandBody;
import io.paradaux.friendlybot.bot.command.DiscordCommand;
import io.paradaux.friendlybot.data.database.models.FGuild;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.EmbedBuilder;
import org.json.JSONArray;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@Command(name="cat", description = "Get yourself a cute cat photo!")
public class CatCommand extends DiscordCommand {

    private static final String CAT_API = "https://api.thecatapi.com/v1/images/search";

    public void execute(FGuild guild, CommandBody body) {
        HttpApi http = new HttpApi(getLogger());
        HttpRequest request = http.plainRequest(CAT_API);

        CompletableFuture<HttpResponse<String>> responseFtr = http.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        if (responseFtr == null) {
            throw new IllegalStateException("Cat: Response was null");
        }

        responseFtr.thenAccept((response) -> {
            EmbedBuilder builder = new EmbedBuilder()
                    .setImage(new JSONArray(response.body()).getJSONObject(0).getString("url"))
                    .setColor(NumberUtils.randomColor())
                    .setFooter("For " + body.getUser().getName());

            body.getMessage().getChannel().sendMessageEmbeds(builder.build()).queue();
        }).join();
    }
}
