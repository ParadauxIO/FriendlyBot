package io.paradaux.friendlybot.commands.fun;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.types.BaseCommand;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.Logger;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherCommand extends BaseCommand {

    private static final String WEATHER_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&APPID=%s";


    public WeatherCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "weather";
        this.help = "View weather information";
    }

    @Override
    protected void execute(CommandEvent event) {

        HttpApi http = new HttpApi(getLogger());

        HttpRequest request = http.plainRequest(WEATHER_API);

        http.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept((response) -> {
            JsonObject weatherData = JsonParser.parseString(response.body()).getAsJsonObject();

            if (!(weatherData.get("cod").getAsInt() == 200)) {

                event.getChannel().sendMessage(new EmbedBuilder()
                        .setColor(0xeb5132)
                        .setTitle("Error: " + weatherData.get("cod").getAsInt())
                        .setDescription("**Message**: " + weatherData.get("message").getAsString())
                        .build()).queue();
                return;
            }

            MessageEmbed embed = new EmbedBuilder()



                    .build();


            event.getChannel().sendMessage(embed).queue();


        }).join();



    }

}
