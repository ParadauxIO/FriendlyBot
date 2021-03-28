package io.paradaux.friendlybot.commands.utility;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.managers.UserSettingsManager;
import io.paradaux.friendlybot.utils.NumberUtils;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.database.UserSettingsEntry;
import io.paradaux.friendlybot.utils.models.types.BaseCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import org.slf4j.Logger;

import java.util.List;

public class ClearColorCommand extends BaseCommand {

    public ClearColorCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
        this.name = "clearcolor";
        this.aliases = new String[]{"clearcolour"};
        this.help = "Clear a custom-color role.";
    }

    @Override
    protected void execute(CommandEvent event) {
        final Message message = event.getMessage();
        final Guild guild = event.getGuild();

        UserSettingsManager settings = UserSettingsManager.getInstance();
        UserSettingsEntry entry = settings.getProfileById(guild.getId(), event.getAuthor().getId());

        if (entry.getCustomColorRole() == null) {
            message.reply("You do not have a custom color.").queue();
            return;
        }

        List<Role> customRoles = guild.getRolesByName(entry.getCustomColorRole(), true);

        if (customRoles.size() == 0) {
            message.reply("An error occurred in that you appear to have an invalid custom color.").queue();
            return;
        }

        guild.removeRoleFromMember(event.getMember(), customRoles.get(0)).queue((success) -> {
            String customColor = entry.getCustomColorRole();
            entry.setCustomColorRole(null);
            settings.updateSettingsProfile(entry);

            if (settings.getProfileCountByColor(guild.getId(), customColor) == 0) {
                customRoles.get(0).delete().queue();
            }

            message.getChannel().sendMessage(new EmbedBuilder()
                    .setColor(NumberUtils.randomColor())
                    .setTitle("Your custom color role has been cleared.")
                    .setDescription("This has not had any effect on your 3-day cooldown, you may not modify your color again until that has expired.")
                    .build()).queue();
        });
    }
}
