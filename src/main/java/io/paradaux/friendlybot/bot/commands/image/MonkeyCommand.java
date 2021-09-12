package io.paradaux.friendlybot.bot.commands.image;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.bot.command.CommandBody;
import io.paradaux.friendlybot.bot.command.DiscordCommand;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
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
public class MonkeyCommand extends DiscordCommand {

    private static final String MONKEY_API = "https://www.placemonkeys.com/500/350?random";
    private static final String IMGUR_API = "https://api.imgur.com/3/upload/";

    public MonkeyCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "monkey";
        this.aliases = new String[0];
        this.help = "Get a monkey image !";
    }

    @Override
    public void execute(FGuild guild, CommandBody body) {

        HttpApi http = new HttpApi(getLogger());

        // Get's the image from the monkey API
        HttpRequest request = http.plainRequest(MONKEY_API);
        http.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray()).thenAccept((response) -> {
            // Send that image to imgur
            String[] headers = {"Authorization", "Client-ID " + getConfig().getImgurClientId()};
            HttpRequest request2 = http.postBytes(IMGUR_API, response.body(), headers);

            HttpResponse<String> response2 = http.sendSync(request2, HttpResponse.BodyHandlers.ofString());
            JSONObject imgurMeta = new JSONObject(response2.body());

            EmbedBuilder builder = new EmbedBuilder()
                    .setColor(NumberUtils.randomColor())
                    .setImage(imgurMeta.getJSONObject("data").getString("link"))
                    .setFooter("For " + event.getAuthor().getName());

            event.getChannel().sendMessage(builder.build()).queue();
        }).join();

    }
}
