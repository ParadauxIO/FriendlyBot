package io.paradaux.csbot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

/**
 * This is a command which provides the user with an invite link to the current discord server.
 *
 * @author Rían Errity
 * @version Last modified for 0.1.0-SNAPSHOT
 * @since 1/11/2020 DD/MM/YY
 * @see io.paradaux.csbot.CSBot
 * */

public class InviteCommand extends Command {

    String name;
    String[] aliases;
    String help;

    public InviteCommand(String name, String[] aliases, String help) {
        this.name = "invite";
        this.aliases = new String[]{"inv", "i"};
        this.help = "Provides the user with an invite link.";
    }

    @Override
    protected void execute(CommandEvent event) {

    }
}
