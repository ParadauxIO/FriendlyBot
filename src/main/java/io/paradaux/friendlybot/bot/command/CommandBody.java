package io.paradaux.friendlybot.bot.command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

public class CommandBody {

    private final JDA jda;
    private final Message message;
    private final MessageChannel channel;
    private final String command;
    private final String[] args;

    public CommandBody(Message message, String command, String[] args) {
        this.jda = message.getJDA();
        this.channel = message.getChannel();
        this.message = message;
        this.command = command;
        this.args = args;
    }

    public JDA getJda() {
        return jda;
    }

    public Message getMessage() {
        return message;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }
}
