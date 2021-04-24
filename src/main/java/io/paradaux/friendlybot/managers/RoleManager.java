package io.paradaux.friendlybot.managers;

import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.requests.restaction.RoleAction;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

public class RoleManager {

    private static RoleManager instance;

    private final Logger logger;
    private final MongoManager mongo;

    public RoleManager(Logger logger, MongoManager mongo) {
        this.logger = logger;
        this.mongo = mongo;
        logger.info("Initialising: Role Manager");
        instance = this;
    }

    public static RoleManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

    public void setupColorRolePivot(Guild guild) {
        Role highestRole = getHighestFrom(guild.getSelfMember());
        guild.createRole().setName("Color Roles Begin Here").queue((role -> guild.modifyRolePositions().selectPosition(role).moveTo(role.getPosition() - 1).queue()));
    }

    public boolean checkForConflicts(Guild guild, String name) {
        return DiscordBotManager.getInstance().getRolesByName(guild.getId(), name).size() != 0;
    }

    public RoleAction createRole(Guild guild, String color) {
        if (checkForConflicts(guild, color)) {
            throw new IllegalStateException("This role already exists.");
        }

        return guild.createRole()
                .setColor(Integer.parseInt(color, 16))
                .setMentionable(false)
                .setName(color);
    }

    /**
     * Determines the highest role (i.e the role displayed when the user speaks) of the member provided.
     * */
    @Nullable
    public Role getHighestFrom(Member member) {
        if (member == null) {
            return null;
        }

        List<Role> roles = member.getRoles();

        if (roles.isEmpty()) {
            return null;
        }

        return roles.stream().min((first, second) -> {
            if (first.getPosition() == second.getPosition()) {
                return 0;
            }

            return first.getPosition() > second.getPosition() ? -1 : 1;
        }).get();
    }
}
