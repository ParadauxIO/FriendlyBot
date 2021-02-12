package io.paradaux.friendlybot.commands.utility;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.managers.RoleManager;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.types.BaseCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import org.slf4j.Logger;

import java.util.List;
import java.util.regex.Pattern;

public class SetColorCommand extends BaseCommand {

    private static final short COOLDOWN = 7;
    private static final Pattern HEX_PATTERN = Pattern.compile("(0x)?[0-9a-f]+");

    private final RoleManager roles;
    private final MongoManager mongo;

    public SetColorCommand(ConfigurationEntry config, Logger logger, RoleManager roles, MongoManager mongo) {
        super(config, logger);
        this.name = "setcolor";
        this.aliases = new String[]{"setcolour"};
        this.roles = roles;
        this.mongo = mongo;
    }

    @Override
    protected void execute(CommandEvent event) {
        final Message message = event.getMessage();
        final Guild guild = event.getGuild();
        final String chosenColor = event.getArgs();

        System.out.println(chosenColor);
        if (!(HEX_PATTERN.matcher(chosenColor).results().count() > 0)) {
            message.getChannel().sendMessage(new EmbedBuilder()
                    .setColor(0xeb5132)
                    .setTitle("Invalid Hex Color")
                    .setDescription("Please format your color as a hex string between `000000` and `FFFFFF`")
                    .build()).queue();
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
            // todo update the database.

            return;
        }

        roles.createRole(guild, event.getArgs()).queue((role -> {
            guild.modifyRolePositions().selectPosition(role).moveTo(separatorRole.getPosition() - 1).queue();
            guild.addRoleToMember(event.getMember(), role).queue();
            // todo update the database
        }));
    }
}
