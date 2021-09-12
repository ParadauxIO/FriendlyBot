package io.paradaux.friendlybot.bot.commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.friendlybot.core.utils.RandomUtils;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.BaseCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

public class CoinFlipCommand extends BaseCommand {

    private static final String COIN_FLIP_GIF = "https://media1.tenor.com/images/49dc6597439a635b1614544d8e090c54/tenor.gif";
    private static final String[] TAILS_HEADS_IMAGES = {"https://cdn.paradaux.io/img/jurx4.png", "https://cdn.paradaux.io/img/ofco7.png"};

    public CoinFlipCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "coinflip";
        this.help = "Flips a coin";
        this.aliases = new String[]{"flip", "cf", "flipcoin"};
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();

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

        event.getChannel().sendMessage(waitingEmbed).queue((sentMessage) -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1200);
                sentMessage.editMessage(resultEmbed).queue();
            } catch (InterruptedException e) {
                getLogger().error("Interrupted whilst shaking the magic eight ball..");
            }
        });

    }
}
