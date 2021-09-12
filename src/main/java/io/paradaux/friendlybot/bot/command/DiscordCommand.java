package io.paradaux.friendlybot.bot.command;

import io.paradaux.friendlybot.core.data.database.models.FGuild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Command(name = "undefined", description = "undefined", permission = "undefined")
public abstract class DiscordCommand {

    private final Logger logger = LoggerFactory.getLogger(DiscordCommand.class);

    private boolean isRegistered;
    private String command;
    private String description;
    private String permission;
    private String[] aliases;

    private List<String> names;

    public abstract void execute(FGuild guild, CommandBody body);

    public void register(String command, String description, String permission, String[] aliases) {
        isRegistered = true;
        this.command = command;
        this.description = description;
        this.permission = permission;
        this.aliases = aliases;
        this.names = new ArrayList<>();
        names.addAll(List.of(aliases));
        names.add(command);
    }

    public void unregister() {
        isRegistered = false;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public Logger getLogger() {
        return logger;
    }

    public String getPermission() {
        return permission;
    }

    public String[] getAliases() {
        return aliases;
    }

    public List<String> getNames() {
        return names;
    }
}
