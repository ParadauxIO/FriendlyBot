package io.paradaux.friendlybot.bot.commands.util;

import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.bot.command.CommandBody;
import io.paradaux.friendlybot.bot.command.DiscordCommand;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import io.paradaux.friendlybot.core.utils.NumberUtils;
import io.paradaux.friendlybot.core.utils.models.database.UserSettingsEntry;
import io.paradaux.friendlybot.managers.RoleManager;
import io.paradaux.friendlybot.managers.UserSettingsManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Command(name = "setcolor", description = "Set your own user color!", permission = "util.usercolor", aliases = {"setcolour"})
public class SetColorCommand extends DiscordCommand {

    private static final short COOLDOWN = 7;
    private static final Pattern HEX_PATTERN = Pattern.compile("(0x)?[0-9a-f]+");

    private final RoleManager roles;

    public SetColorCommand(RoleManager roles) {
        this.roles = roles;
    }

    @Override
    public void execute(FGuild guild, CommandBody body) {
        final Message message = body.getMessage();
        final Guild jDuild = guild.getGuild();
        final String chosenColor = body.getArgStr().replace("0x", "").toUpperCase();

        if (body.isArgsEmpty()) {
            body.getChannel().sendMessageEmbeds(new EmbedBuilder()
                    .setColor(NumberUtils.randomColor())
                    .setTitle("Setting Your Uniquely Coloured Role")
                    .setDescription("To get your own coloured role, run `;setcolour <hex>`. This has a 3 day cooldown, so make sure "
                            + "you're certain! After 3 days you can run this command again to change the colour\n\n"
                            + "**Want to remove the colour?**\nYou can do this at any time using `;clearcolour` it doesn't affect your cooldown!")
                    .build()).queue();
            return;
        }

        if (!(HEX_PATTERN.matcher(chosenColor).results().count() > 0)) {
            message.getChannel().sendMessageEmbeds(new EmbedBuilder()
                    .setColor(0xeb5132)
                    .setTitle("Invalid Hex Color")
                    .setDescription("Please format your color as a hex string between `000000` and `FFFFFF`")
                    .build()).queue();
            return;
        }

        UserSettingsManager settings = UserSettingsManager.getInstance();
        UserSettingsEntry entry = settings.getProfileById(jDuild.getId(), body.getUser().getId());
        if (entry.getLastSetColor() != null && !settings.hasCooldownElapsed(entry)) {
            message.reply("You must wait until your cooldown expires before running this command again.").queue();
            return;
        }

        if (entry.getCustomColorRole() != null) {
            message.reply("Please clear your existing color role by using `;clearcolor` before setting a new color.").queue();
            return;
        }

        List<Role> allRoles = jDuild.getRolesByName("Color Roles Begin Here", true);
        if (allRoles.isEmpty()) {
            throw new RuntimeException("Color role separator not present.");
        }

        Role separatorRole = allRoles.get(0);

        if (roles.checkForConflicts(jDuild, chosenColor)) {
            // role already exists, let's not create a duplicate
            jDuild.addRoleToMember(body.getMember(), jDuild.getRolesByName(chosenColor, true).get(0)).queue();

            entry.setCustomColorRole(chosenColor)
                    .setLastSetColor(new Date());

            settings.updateSettingsProfile(entry);
            return;
        }

        roles.createRole(jDuild, chosenColor).queue((role -> {
            jDuild.modifyRolePositions().selectPosition(role).moveTo(separatorRole.getPosition() - 1).queue();
            jDuild.addRoleToMember(body.getMember(), role).queue();

            entry.setCustomColorRole(chosenColor)
                    .setLastSetColor(new Date());

            settings.updateSettingsProfile(entry);
        }));
    }
}
