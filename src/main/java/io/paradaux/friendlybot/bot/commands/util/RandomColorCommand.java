package io.paradaux.friendlybot.bot.commands.util;

import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.bot.command.CommandBody;
import io.paradaux.friendlybot.bot.command.DiscordCommand;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import io.paradaux.friendlybot.core.utils.ImageUtils;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.EmbedBuilder;
import org.json.JSONObject;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Command(name = "randomcolor", description = "Get a random Hex RGB Color", permission = "commands.randomcolor", aliases = {"randomcolour"})
public class RandomColorCommand extends DiscordCommand {

    private static final String IMGUR_API = "https://api.imgur.com/3/upload/";

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
            body.getMessage().reply("An error occurred whilst creating a solid-color image").queue();
            return;
        }

        HttpApi http = new HttpApi(getLogger());
        String[] headers = {"Authorization", "Client-ID " + getConfig().getImgurClientId()};

        HttpRequest request = http.postBytes(IMGUR_API, imageRaw, headers);

        http.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept((response -> {
            JSONObject imgurMeta = new JSONObject(response.body());
            embed.setImage(imgurMeta.getJSONObject("data").getString("link"));
            body.getChannel().sendMessage(embed.build()).queue();
        })).join();
    }
}
