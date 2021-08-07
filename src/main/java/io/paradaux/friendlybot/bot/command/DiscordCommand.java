package io.paradaux.friendlybot.bot.command;

import io.paradaux.friendlybot.core.database.models.FGuild;

@Command(name = "undefined", description = "undefined")
public abstract class DiscordCommand {

    private boolean isRegistered;
    private String command;
    private String description;

    public abstract void execute(FGuild guild, CommandBody body);

    public void register(String command, String description) {
        isRegistered = true;
        this.command = command;
        this.description = description;
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
}
