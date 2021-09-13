package io.paradaux.friendlybot.bot.commands.joke;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.bot.command.CommandBody;
import io.paradaux.friendlybot.bot.command.DiscordCommand;
import io.paradaux.friendlybot.bot.commands.image.MemeCommand;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import io.paradaux.friendlybot.core.utils.HttpUtils;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.friendlybot.core.utils.RandomUtils;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.BaseCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.HashSet;

@Command(name = "monke", description = "MONKE.", permission = "command.monke", aliases = {})
public class MonkeCommand extends DiscordCommand {

    private static final HashSet<String> MONKE_IDS = new HashSet<>();

    static {
        Collections.addAll(MONKE_IDS, "289081819", "200213111");
    }

    @Override
    public void execute(FGuild guild, CommandBody body) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("template_id", new RandomUtils().fromCollection(MONKE_IDS))
                .addFormDataPart("username", getConfig().getImgflipUsername())
                .addFormDataPart("password", getConfig().getImgflipPassword());

        String[] lines = String.join(" ", body.getArgs()).split("\\|");

        if (lines.length != 2) {
            syntaxError(body.getMessage());
        }

        bodyBuilder.addFormDataPart("text0", lines[0]);
        bodyBuilder.addFormDataPart("text1", lines[1]);

        RequestBody reqBody = bodyBuilder.build();

        Request request = new Request.Builder()
                .url(MemeCommand.CAPTION_MEMES_API)
                .method("POST", reqBody)
                .build();

        HttpUtils.sendAsync(client, request).thenAccept((response -> {
            if (response.body() == null) {
                throw new IllegalStateException("No response received.");
            }

            try (Reader reader = response.body().charStream()) {
                int charInt;
                StringBuilder strBuilder = new StringBuilder();
                while ((charInt = reader.read()) != -1) {
                    strBuilder.append((char) charInt);
                }

                JSONObject responseJson = new JSONObject(strBuilder.toString());

                MessageEmbed embed = new EmbedBuilder().setImage(responseJson.getJSONObject("data").getString("url")).setColor(NumberUtils.randomColor()).setFooter("For " + body.getUser().getName()).build();

                body.getChannel().sendMessage(embed).queue();

            } catch (IOException ok) {
                getLogger().error("Error occurred whilst interacting with Imgflip");
            }
        }));
    }
}
