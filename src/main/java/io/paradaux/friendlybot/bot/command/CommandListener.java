package io.paradaux.friendlybot.bot.command;

import io.paradaux.friendlybot.bot.command.exception.CommandException;
import io.paradaux.friendlybot.core.cache.GuildCache;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

public class CommandListener extends ListenerAdapter {

    private final ArrayList<DiscordCommand> commands;

    public CommandListener() {
        commands = new ArrayList<>();
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        // Get the guild
        FGuild guild = GuildCache.getInstance().getGuild(event.getGuild().getId());

        // Message stuff
        Message message = event.getMessage();
        String messageContent = message.getContentRaw();

        // If it isn't a valid command
        if (!messageContent.startsWith(guild.getCommandPrefix())) {
            return;
        }

        // Command body
        String command = messageContent.substring(guild.getCommandPrefix().length());
        String[] args = {};

        // If there's arguments, parse them.
        if (messageContent.contains(" ")) {
            String[] components = command.split(" ");
            args = Arrays.copyOfRange(components, 1, components.length);
            command = components[0];
        }

        if (command.equalsIgnoreCase("help")) {
            helpMenu(message);
            return;
        }

        for (DiscordCommand c : commands) {
            if (c.getCommand().equalsIgnoreCase(command)) {
                c.execute(guild, new CommandBody(message, command, c.getPermission(), c.getDescription(), args));
                break;
            }
        }

    }


    /**
     * Returns a list of commands as a message as a reply.
     * */
    public void helpMenu(Message message) {
        EmbedBuilder builder = new EmbedBuilder();

        StringBuilder descBuilder = new StringBuilder().append("\n\n");
        for (DiscordCommand c : commands) {
            descBuilder.append("\\➡ **").append(c.getCommand()).append("**").append(" » ").append(c.getDescription());
        }

        descBuilder.append("\n\n");

        builder.setAuthor("**FriendlyBot**: Commands");
        builder.setDescription(descBuilder);
        builder.setTimestamp(Instant.now());
        builder.setFooter("N.B: Some commands may be role-locked.");

        message.getChannel().sendMessageEmbeds(builder.build()).queue();
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

        command.register(cmd.name(), cmd.description(), cmd.permission());

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
            throw new CommandException("This command is not registered.");
        }
    }
}
