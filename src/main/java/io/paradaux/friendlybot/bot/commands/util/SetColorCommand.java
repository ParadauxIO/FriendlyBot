package io.paradaux.friendlybot.bot.commands.util;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.database.UserSettingsEntry;
import io.paradaux.friendlybot.core.utils.models.types.BaseCommand;
import io.paradaux.friendlybot.managers.RoleManager;
import io.paradaux.friendlybot.managers.UserSettingsManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import org.slf4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Command(name = "", description = "", permission = "", aliases = {})
public class SetColorCommand extends BaseCommand {

    private static final short COOLDOWN = 7;
    private static final Pattern HEX_PATTERN = Pattern.compile("(0x)?[0-9a-f]+");

    private final RoleManager roles;

    public SetColorCommand(ConfigurationEntry config, Logger logger, RoleManager roles) {
        super(config, logger);
        this.name = "setcolor";
        this.aliases = new String[]{"setcolour"};
        this.roles = roles;
    }

    @Override
    protected void execute(CommandEvent event) {
        final Message message = event.getMessage();
        final Guild guild = event.getGuild();
        final String chosenColor = event.getArgs().replace("0x", "").toUpperCase();

        if (event.getArgs().isEmpty()) {
            event.getChannel().sendMessage(new EmbedBuilder()
                    .setColor(NumberUtils.randomColor())
                    .setTitle("Setting Your Uniquely Coloured Role")
                    .setDescription("To get your own coloured role, run `;setcolour <hex>`. This has a 3 day cooldown, so make sure "
                            + "you're certain! After 3 days you can run this command again to change the colour\n\n"
                            + "**Want to remove the colour?**\nYou can do this at any time using `;clearcolour` it doesn't affect your cooldown!")
                    .build()).queue();
            return;
        }

        if (!(HEX_PATTERN.matcher(chosenColor).results().count() > 0)) {
            message.getChannel().sendMessage(new EmbedBuilder()
                    .setColor(0xeb5132)
                    .setTitle("Invalid Hex Color")
                    .setDescription("Please format your color as a hex string between `000000` and `FFFFFF`")
                    .build()).queue();
            return;
        }

        UserSettingsManager settings = UserSettingsManager.getInstance();
        UserSettingsEntry entry = settings.getProfileById(guild.getId(), event.getAuthor().getId());
        if (entry.getLastSetColor() != null && !settings.hasCooldownElapsed(entry)) {
            message.reply("You must wait until your cooldown expires before running this command again.").queue();
            return;
        }

        if (entry.getCustomColorRole() != null) {
            message.reply("Please clear your existing color role by using `;clearcolor` before setting a new color.").queue();
            return;
        }

        List<Role> allRoles = guild.getRolesByName("Color Roles Begin Here", true);
        if (allRoles.isEmpty()) {
            throw new RuntimeException("Color role separator not present.");
        }

        Role separatorRole = allRoles.get(0);

        if (roles.checkForConflicts(guild, chosenColor)) {
            // role already exists, let's not create a duplicate
            guild.addRoleToMember(event.getMember(), guild.getRolesByName(chosenColor, true).get(0)).queue();

            entry.setCustomColorRole(chosenColor)
                    .setLastSetColor(new Date());

            settings.updateSettingsProfile(entry);
            return;
        }

        roles.createRole(guild, chosenColor).queue((role -> {
            guild.modifyRolePositions().selectPosition(role).moveTo(separatorRole.getPosition() - 1).queue();
            guild.addRoleToMember(event.getMember(), role).queue();

            entry.setCustomColorRole(chosenColor)
                    .setLastSetColor(new Date());

            settings.updateSettingsProfile(entry);
        }));
    }
}
