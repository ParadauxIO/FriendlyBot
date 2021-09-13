package io.paradaux.friendlybot.bot.commands.util;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.bot.command.CommandBody;
import io.paradaux.friendlybot.bot.command.DiscordCommand;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import io.paradaux.friendlybot.core.utils.ImageUtils;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.BaseCommand;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.EmbedBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Command(name = "", description = "", permission = "", aliases = {})
public class RandomColorCommand extends DiscordCommand {

    private static final String IMGUR_API = "https://api.imgur.com/3/upload/";

    public RandomColorCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "randomcolor";
        this.aliases = new String[]{"randomcolour"};
        this.help = "Get a random Hex RGB Color";
    }

    @Override
    public void execute(FGuild guild, CommandBody body) {
        short color = NumberUtils.randomColor();

        EmbedBuilder embed = new EmbedBuilder()
                .setColor(color)
                .setTitle("Here's your random color: " + Integer.toHexString(color));

        BufferedImage image = ImageUtils.createSolidColor(250, 250, color);
        byte[] imageRaw;
        try {
             imageRaw = ImageUtils.imageToBytes(image, "png");
        } catch (IOException ok) {
            getLogger().error("An error occurred while creating a solid-color image.");
            event.getMessage().reply("An error occurred whilst creating a solid-color image").queue();
            return;
        }

        HttpApi http = new HttpApi(getLogger());
        String[] headers = {"Authorization", "Client-ID " + getConfig().getImgurClientId()};

        HttpRequest request = http.postBytes(IMGUR_API, imageRaw, headers);

        http.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept((response -> {
            JSONObject imgurMeta = new JSONObject(response.body());
            embed.setImage(imgurMeta.getJSONObject("data").getString("link"));
            event.getChannel().sendMessage(embed.build()).queue();
        })).join();
    }
}
