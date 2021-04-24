package io.paradaux.friendlybot.commands.staff.technician;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.managers.RoleManager;
import io.paradaux.friendlybot.utils.StringUtils;
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

        event.getChannel().sendMessage("Are you going to use the custom color roles? (yes, no)").queue(message -> waiter.waitForEvent(MessageReceivedEvent.class,
                messageEvent -> sameOrigin(event.getMessage(), messageEvent.getMessage()), messageEvent -> {
            boolean usingColorRoles = StringUtils.parseBoolean(messageEvent.getMessage().getContentRaw());

            if (usingColorRoles) {
                messageEvent.getChannel().sendMessage("Creating a roll called `Color Roles Begin Here`. Please move this role above where you would like color roles to be put. **N.B**: This has to be below the bot's highest role.)");
                RoleManager.getInstance().setupColorRolePivot(event.getGuild());
            }
                event.getChannel().sendMessage("Are you going to use Email Verification? (yes, no)").queue(message2 -> waiter.waitForEvent(MessageReceivedEvent.class,
                    messageEvent2 -> sameOrigin(event.getMessage(), messageEvent.getMessage()), messageEvent2 -> {
                boolean usingEmail = StringUtils.parseBoolean(messageEvent.getMessage().getContentRaw());
                    event.getChannel().sendMessage("Are you going to use the ModMail ? (yes, no)").queue(message3 -> waiter.waitForEvent(MessageReceivedEvent.class,
                            messageEvent3 -> sameOrigin(event.getMessage(), messageEvent.getMessage()), messageEvent3 -> {
                    boolean usingModMail = StringUtils.parseBoolean(messageEvent.getMessage().getContentRaw());

                            event.getChannel().sendMessage("Are you going to use the custom color roles? (yes, no)").queue(message4 -> waiter.waitForEvent(MessageReceivedEvent.class,
                                    messageEvent4 -> sameOrigin(event.getMessage(), messageEvent.getMessage()), messageEvent4 -> {

                                    }));


                    }));

                }));


            // Going to use roles
            // Going to use modmail
            // Going to use email verification


        }));
    }


    public boolean sameOrigin(Message message1, Message message2) {
        if (!message1.getChannel().getId().equals(message2.getChannel().getId())) {
            return false;
        }

        return message1.getAuthor().getId().equals(message2.getAuthor().getId());
    }

}
