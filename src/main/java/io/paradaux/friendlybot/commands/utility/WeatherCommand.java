package io.paradaux.friendlybot.commands.utility;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.utils.NumberUtils;
import io.paradaux.friendlybot.utils.StringUtils;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.types.BaseCommand;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.Logger;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherCommand extends BaseCommand {

    private static final String WEATHER_API = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";
    private static final String MORE_INFORMATION_LINK = "https://openweathermap.org/city/%d";
    private static final String WEATHER_ICON_LINK = "http://openweathermap.org/img/wn/%s@4x.png";
    private static final String TEMPERATURE_FORMAT = "%.2f°C (%.2f°F)";

    public WeatherCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "weather";
        this.help = "View weather information";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();

        HttpApi http = new HttpApi(getLogger());

        if (event.getArgs().isEmpty()) {
            respondSyntaxError(message, ";weather <Location>");
            return;
        }

        String place = StringUtils.toTitleCase(event.getArgs());

        HttpRequest request = http.plainRequest(String.format(WEATHER_API, place.replace(" ", "%20"), getConfig().getWeatherApiKey()));

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

            JsonObject coordinates = weatherData.getAsJsonObject("coord");
            JsonObject temperatureData = weatherData.getAsJsonObject("main");
            double currentTemperatureKelvin = temperatureData.get("temp").getAsDouble();
            double feelsLikeTemperatureKelvin = temperatureData.get("feels_like").getAsDouble();
            double minimumTemperatureKelvin = temperatureData.get("temp_min").getAsDouble();
            double maximumTemperatureKelvin = temperatureData.get("temp_max").getAsDouble();

            System.out.printf((WEATHER_ICON_LINK) + "%n", weatherData.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString());

            MessageEmbed embed = new EmbedBuilder()
                    .setColor(NumberUtils.randomColor())
                    .setAuthor("Weather » " + place, String.format(MORE_INFORMATION_LINK, weatherData.get("id").getAsInt()),
                            String.format(WEATHER_ICON_LINK, weatherData.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon")))
                    .setDescription("Weather Information reflects the current weather conditions, this information is provided courtesy of [OpenWeatherMap](https://openweathermap.org/).")
                    .addField("Location:", String.format("%s: %.4f°N, %.4f°W", place, coordinates.get("lon").getAsFloat(), coordinates.get("lat").getAsFloat()), true)
                    .addField("Current Temperature: ", String.format(TEMPERATURE_FORMAT, NumberUtils.kelvinToCelsius(currentTemperatureKelvin),
                            NumberUtils.kelvinToFahrenheit(currentTemperatureKelvin)), true)
                    .addField("Feels like:", "", true)
                    .addField("Pressure", "", true)
                    .addField("Humidity", "", true)
                    .addField("Visibility", "", true)
                    .addField("Wind Conditions:", "", true)
                    .addField("Minimum Temperature Expected Today:", "", false)
                    .addField("Maximum Temperature Expected Today:", "", false)
                    .build();

            event.getChannel().sendMessage(embed).queue();
        }).join();



    }

}
