package io.paradaux.friendlybot.managers;

import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.restaction.RoleAction;
import org.slf4j.Logger;

public class RoleManager {

    private static RoleManager instance;

    private final Logger logger;
    private final MongoManager mongo;

    public RoleManager(Logger logger, MongoManager mongo) {
        this.logger = logger;
        this.mongo = mongo;
        instance = this;
    }

    public static RoleManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
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

}
