package io.paradaux.friendlybot.commands.staff.technician;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.types.PrivilegedCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;

public class SetupCommand extends PrivilegedCommand {

    private final EventWaiter waiter;

    public SetupCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager, EventWaiter waiter) {
        super(config, logger, permissionManager);
        this.waiter = waiter;
        this.name = "setup";
        this.help = "Configures guild settings";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!isStaff(event.getGuild(), event.getAuthor().getId())) {
            return;
        }

        event.reply("Beginning to configure this guild.");

        EmbedBuilder builder = new EmbedBuilder();

        event.getChannel().sendMessage("Please set the title.").queue(message -> waiter.waitForEvent(MessageReceivedEvent.class,
                messageEvent -> sameOrigin(event.getMessage(), messageEvent.getMessage()), messageEvent -> {

            // Going to use roles
            // Going to use modmail
            // Going to use email verification
            // Going to use color roles




        }));
    }


    public boolean getBoolean(String message) {
        
    }



    public boolean sameOrigin(Message message1, Message message2) {
        if (!message1.getChannel().getId().equals(message2.getChannel().getId())) {
            return false;
        }

        return message1.getAuthor().getId().equals(message2.getAuthor().getId());
    }

}
