package io.paradaux.friendlybot.bot.commands.util;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.bot.command.CommandBody;
import io.paradaux.friendlybot.bot.command.DiscordCommand;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.database.UserSettingsEntry;
import io.paradaux.friendlybot.core.utils.models.types.BaseCommand;
import io.paradaux.friendlybot.managers.UserSettingsManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import org.slf4j.Logger;

import java.util.List;

@Command(name = "clearcolor", description = "Clear a custom-color role.", permission = "util.color", aliases = {"clearcolour"})
public class ClearColorCommand extends DiscordCommand {

    @Override
    public void execute(FGuild guild, CommandBody body) {
        final Message message = event.getMessage();

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
