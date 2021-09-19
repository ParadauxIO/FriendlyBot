package io.paradaux.friendlybot.bot.command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class CommandBody {

    private final JDA jda;

    private final String command;
    private final String permission;
    private final String description;

    private final String[] args;
    private final String[] aliases;

    private final Message message;
    private final MessageChannel channel;
    private final Member member;
    private final User user;

    public CommandBody(Message message, String command, String permission, String description, String[] args, String[] aliases) {
        this.jda = message.getJDA();

        this.command = command;
        this.permission = permission;
        this.description = description;

        this.args = args;
        this.aliases = aliases;

        this.message = message;
        this.channel = message.getChannel();
        this.member = message.getMember();
        if (this.member == null) {
            throw new IllegalStateException("Member cannot be null");
        }
        this.user = member.getUser();
    }

    public String getArgStr() {
        return String.join(" ", getArgs());
    }

    public boolean isArgsEmpty() {
        return getArgs().length == 0;
    }

    public JDA getJda() {
        return jda;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public Message getMessage() {
        return message;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public Member getMember() {
        return member;
    }

    public User getUser() {
        return user;
    }

    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }
}
