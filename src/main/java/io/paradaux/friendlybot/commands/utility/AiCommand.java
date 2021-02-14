package io.paradaux.friendlybot.commands.utility;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.managers.SettingsManager;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.database.UserSettingsEntry;
import io.paradaux.friendlybot.utils.models.types.PrivilegedCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;

public class AiCommand extends PrivilegedCommand {

    public AiCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager) {
        super(config, logger, permissionManager);
        this.name = "ai";
        this.help = "Ai Utilities";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();

        if (event.getArgs().isEmpty()) {
            respondSyntaxError(message, ";ai <optout/optin/nth/ignore>");
            return;
        }

        String[] args = getArgs(event.getArgs());
        SettingsManager settings = SettingsManager.getInstance();

        switch (args[0]) {
            case "optout": {
                UserSettingsEntry entry = settings.getProfileById(event.getGuild().getId(), event.getAuthor().getId())
                        .setDoTrainAi(false);
                settings.updateSettingsProfile(entry);
                event.getChannel().sendMessage(new EmbedBuilder()
                        .setTitle("You have opted out of our chatter bot training system.")
                        .setDescription("in the near future their will be a facility to delete already existing data recorded.")
                        .build()).queue();
                break;
            }

            case "optin": {
                UserSettingsEntry entry = settings.getProfileById(event.getGuild().getId(), event.getAuthor().getId())
                        .setDoTrainAi(true);
                settings.updateSettingsProfile(entry);
                event.getChannel().sendMessage(new EmbedBuilder()
                        .setTitle("You have opted in to our chatter bot training system.")
                        .setDescription("in the near future their will be a facility to delete already existing data recorded.")
                        .build()).queue();
                break;
            }

            case "deleteall": {



            }

            case "nth": {
                // TODO stub
            }
        }
        
    }
}
