package io.paradaux.friendlybot.bot.command;

import io.paradaux.friendlybot.bot.command.exception.CommandException;
import io.paradaux.friendlybot.core.cache.GuildCache;
import io.paradaux.friendlybot.data.database.models.FGuild;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandListener extends ListenerAdapter {

    private final ArrayList<DiscordCommand> commands;

    public CommandListener() {
        commands = new ArrayList<>();
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        FGuild guild = GuildCache.getInstance().getGuild(event.getGuild().getId());

        Message message = event.getMessage();
        String messageContent = message.getContentRaw();
        String[] commandComponents = messageContent.substring(guild.getCommandPrefix().length()).split(" ");

        String command = commandComponents[0];
        String[] args = Arrays.copyOfRange(commandComponents, 1, commandComponents.length);

        CommandBody body = new CommandBody(message, command, args);
        if (messageContent.startsWith(guild.getCommandPrefix())) {
            switch (messageContent.substring(guild.getCommandPrefix().length())) {
                case "help": {
                    helpMenu(message);
                    break;
                }

                default: {
                    for (DiscordCommand c : commands) {
                        if (c.getCommand().equals(command)) {
                            c.execute(guild, body);
                        }
                    }
                    break;
                }
            }
        }
    }


    /**
     * Returns a list of commands as a message as a reply.
     * */
    public void helpMenu(Message message) {
        EmbedBuilder builder = new EmbedBuilder();
        StringBuilder descBuilder = new StringBuilder();

        descBuilder.append("**FriendlyBot**: Commands\n")
                .append("\n");

        for (DiscordCommand c : commands) {
            descBuilder.append(c.getCommand())
                    .append(" » ")
                    .append(c.getDescription());
        }

        descBuilder.append("\n")
                .append("**N.B**: Some commands may be role-locked.");

        builder.setDescription(descBuilder);

        // TODO
    }


    /**
     * Registers the requested command.
     * */
    public void registerCommand(DiscordCommand command) {
        if (command.isRegistered()) {
            throw new CommandException("Command is already marked as registered.");
        }

        Class<?> clazz = command.getClass();
        if (!clazz.isAnnotationPresent(Command.class)) {
            throw new CommandException("Provided Argument is not a valid command. Not annotated as command.");
        }

        Command cmd = clazz.getAnnotation(Command.class);
        command.register(cmd.name(), cmd.description());

        for (DiscordCommand c : commands) {
            if (c.getCommand().equals(command.getCommand())) {
                throw new CommandException("A command by this name has already been registered.");
            }
        }

        commands.add(command);
    }


    /**
     * Unregisters the requested command.
     * @throws CommandException If the provided command has not been registered.
     * */
    public void removeCommand(DiscordCommand command) {
        if (!commands.remove(command)) {
            throw new CommandException("This command has already been registered.");
        }
    }
}
