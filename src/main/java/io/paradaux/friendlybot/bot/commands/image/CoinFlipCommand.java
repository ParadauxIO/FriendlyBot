package io.paradaux.friendlybot.bot.commands.image;

import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.bot.command.CommandBody;
import io.paradaux.friendlybot.bot.command.DiscordCommand;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.friendlybot.core.utils.RandomUtils;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

@Command(name = "coinflip", description = "Provides a heads/tails result on a €2 coin", permission = "command.coinflip",
        aliases = {"flip", "cf", "flipcoin"})
public class CoinFlipCommand extends DiscordCommand {

    private static final String COIN_FLIP_GIF = "https://media1.tenor.com/images/49dc6597439a635b1614544d8e090c54/tenor.gif";
    private static final String[] TAILS_HEADS_IMAGES = {"https://cdn.paradaux.io/img/jurx4.png", "https://cdn.paradaux.io/img/ofco7.png"};

    @Override
    public void execute(FGuild guild, CommandBody body) {
        int result = new RandomUtils().pickRandomNumber(0, 1);

        MessageEmbed waitingEmbed = new EmbedBuilder()
                .setColor(NumberUtils.randomColor())
                .setTitle("Flipping a coin...")
                .setImage(COIN_FLIP_GIF)
                .build();

        MessageEmbed resultEmbed = new EmbedBuilder()
                .setColor(NumberUtils.randomColor())
                .setTitle("The coin showed: " + (result == 0 ? "TAILS" : "HEADS"))
                .setImage(TAILS_HEADS_IMAGES[result])
                .build();

        body.getChannel().sendMessageEmbeds(waitingEmbed).queue((sentMessage) -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1200);
                sentMessage.editMessageEmbeds(resultEmbed).queue();
            } catch (InterruptedException e) {
                getLogger().error("Interrupted whilst shaking the magic eight ball..");
            }
        });

    }
}
