package io.paradaux.friendlybot.bot.command;

import io.paradaux.friendlybot.FBApplication;
import io.paradaux.friendlybot.core.data.config.FConfiguration;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import io.paradaux.friendlybot.core.utils.models.enums.EmbedColour;
import io.paradaux.friendlybot.core.utils.models.exceptions.NoSuchUserException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    public void syntaxError(Message message) {
        message.addReaction("\uD83D\uDEAB").queue();
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(EmbedColour.ISSUE.getColour());
        builder.setAuthor(message.getAuthor().getAsTag(), null, message.getAuthor().getAvatarUrl());
        builder.setDescription("There was an error in your syntax.");
        builder.setFooter("FriendlyBot v" + FBApplication.VERSION);

        message.getChannel().sendMessageEmbeds(builder.build()).queue();
    }

    /**
     * Used for commands which take a user as a parameter. Tries three ways of parsing the user. The first being by an @mention, the second
     * by a tag (Username#Discriminator) then finally via their discord id. Returns the user object if found, otherwise null.
     * */
    @CheckReturnValue
    @Nullable
    public User parseTarget(Message message, String userInput) throws NoSuchUserException {
        try {
            if (message.getMentionedMembers().size() == 1) {
                return message.getMentionedMembers().get(0).getUser();
            }

            User user;
            try {
                user = message.getGuild().getJDA().getUserByTag(userInput);
            } catch (IllegalArgumentException ex) {
                user = message.getJDA().retrieveUserById(userInput).submit().get();
            }

            return user;
        } catch (InterruptedException | ExecutionException | NumberFormatException exception) {
            throw new NoSuchUserException(exception.getMessage());
        }
    }

    /**
     * Gets a message by guild and user id.
     * */
    @CheckReturnValue
    @Nullable
    public Member retrieveMember(Guild guild, String userId) {
        try {
            return guild.retrieveMemberById(userId)
                    .submit()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Interrupted Exception", e);
        }

        return null;
    }

    /**
     * Gets a message by guild and User.
     * */
    @CheckReturnValue
    @Nullable
    public Member retrieveMember(Guild guild, User user) {
        try {
            return guild.retrieveMember(user)
                    .submit()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Interrupted Exception", e);
        }

        return null;
    }

    public FConfiguration getConfig() {
        return FBApplication.getConfig();
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
