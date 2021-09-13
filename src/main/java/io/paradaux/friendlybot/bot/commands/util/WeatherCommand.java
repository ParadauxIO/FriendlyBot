package io.paradaux.friendlybot.bot.commands.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.bot.command.CommandBody;
import io.paradaux.friendlybot.bot.command.DiscordCommand;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.friendlybot.core.utils.StringUtils;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.BaseCommand;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.Logger;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Command(name = "", description = "", permission = "", aliases = {})
public class WeatherCommand extends DiscordCommand {

    private static final String WEATHER_API = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";
    private static final String MORE_INFORMATION_LINK = "https://openweathermap.org/city/%d";
    private static final String WEATHER_ICON_LINK = "https://openweathermap.org/img/wn/%s@4x.png";
    private static final String TEMPERATURE_FORMAT = "%.2f°C (%.2f°F)";
    private static final String WIND_FORMAT = "%.1fm/s %.0f° (%s)";

    public WeatherCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "weather";
        this.help = "View weather information";
    }

    @Override
    public void execute(FGuild guild, CommandBody body) {
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

            JsonObject coordinateData = weatherData.getAsJsonObject("coord");
            JsonObject temperatureData = weatherData.getAsJsonObject("main");
            JsonObject windData = weatherData.getAsJsonObject("wind");
            JsonObject climateData = weatherData.getAsJsonArray("weather").get(0).getAsJsonObject();

            double currentTemperatureKelvin = temperatureData.get("temp").getAsDouble();
            double feelsLikeTemperatureKelvin = temperatureData.get("feels_like").getAsDouble();
            double minimumTemperatureKelvin = temperatureData.get("temp_min").getAsDouble();
            double maximumTemperatureKelvin = temperatureData.get("temp_max").getAsDouble();

            MessageEmbed embed = new EmbedBuilder()
                    .setColor(NumberUtils.randomColor())
                    .setAuthor("Weather » " + place, String.format(MORE_INFORMATION_LINK, weatherData.get("id").getAsInt()),
                            String.format(WEATHER_ICON_LINK, climateData.get("icon").getAsString()))
                    .setDescription(climateData.get("main").getAsString() + " » " + climateData.get("description").getAsString())
                    .addField("Location:", String.format("%s: %.4f°N, %.4f°W", place, coordinateData.get("lon").getAsFloat(), coordinateData.get("lat").getAsFloat()), true)
                    .addField("Current Temperature: ", String.format(TEMPERATURE_FORMAT, NumberUtils.kelvinToCelsius(currentTemperatureKelvin),
                            NumberUtils.kelvinToFahrenheit(currentTemperatureKelvin)), true)
                    .addField("Feels like:", String.format(TEMPERATURE_FORMAT, NumberUtils.kelvinToCelsius(feelsLikeTemperatureKelvin),
                            NumberUtils.kelvinToFahrenheit(feelsLikeTemperatureKelvin)), true)
                    .addField("Pressure", String.format("%.1fhPa", temperatureData.get("pressure").getAsDouble()), true)
                    .addField("Humidity", String.format("%.1f%%", temperatureData.get("humidity").getAsDouble()), true)
                    .addField("Visibility", String.format("%.2fkm", weatherData.get("visibility").getAsDouble() / 1000D), true)
                    .addField("Wind Conditions:", String.format(WIND_FORMAT, windData.get("speed").getAsDouble(), windData.get("deg").getAsDouble(),
                            StringUtils.headingToShortCardinalDirection(windData.get("deg").getAsDouble())), true)
                    .addField("Minimum Temperature Expected Today:", String.format(TEMPERATURE_FORMAT, NumberUtils.kelvinToCelsius(minimumTemperatureKelvin),
                            NumberUtils.kelvinToFahrenheit(minimumTemperatureKelvin)), false)
                    .addField("Maximum Temperature Expected Today:", String.format(TEMPERATURE_FORMAT, NumberUtils.kelvinToCelsius(maximumTemperatureKelvin),
                            NumberUtils.kelvinToFahrenheit(maximumTemperatureKelvin)), false)
                    .setFooter("Weather Information reflects the current weather conditions, this information is provided courtesy of OpenWeatherMap.org")
                    .build();

            event.getChannel().sendMessage(embed).queue();
        }).join();
    }
}
