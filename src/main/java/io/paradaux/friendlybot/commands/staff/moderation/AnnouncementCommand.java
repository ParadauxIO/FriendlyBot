package io.paradaux.friendlybot.commands.staff.moderation;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.types.PrivilegedCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;

public class AnnouncementCommand extends PrivilegedCommand {

    private final EventWaiter waiter;

    public AnnouncementCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager, EventWaiter waiter) {
        super(config, logger, permissionManager);
        this.waiter = waiter;
        this.name = "announcement";
        this.help = "Creates announcements.";
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply("Beginning to build an announcement.");

        EmbedBuilder builder = new EmbedBuilder();

        event.getChannel().sendMessage("Please set the title.").queue(message -> waiter.waitForEvent(MessageReceivedEvent.class, messageEvent -> sameOrigin(event.getMessage(), messageEvent.getMessage()), messageEvent -> {
            final String title = messageEvent.getMessage().getContentRaw();

            event.getChannel().sendMessage("Please set the content.").queue(message2 -> waiter.waitForEvent(MessageReceivedEvent.class, messageEvent2 -> sameOrigin(event.getMessage(), messageEvent2.getMessage()), messageEvent2 -> {
                final String content = messageEvent2.getMessage().getContentRaw();

                event.getChannel().sendMessage("Please set the picture (use a link!)").queue(message3 -> waiter.waitForEvent(MessageReceivedEvent.class, messageEvent3 -> sameOrigin(event.getMessage(), messageEvent3.getMessage()), messageEvent3 -> {
                    final String imageUrl = messageEvent3.getMessage().getContentRaw();

                    event.getChannel().sendMessage("Please set the link").queue(message4 -> waiter.waitForEvent(MessageReceivedEvent.class, messageEvent4 -> sameOrigin(event.getMessage(), messageEvent4.getMessage()), messageEvent4 -> {
                        builder.setAuthor(title, messageEvent4.getMessage().getContentRaw(), imageUrl).setThumbnail(imageUrl).setDescription(content);

                        message.getChannel().sendMessage(builder.build()).queue(message5 -> event.getChannel().sendMessage("Where should I send this? (type null for nowhere)").queue(message6 -> waiter.waitForEvent(MessageReceivedEvent.class, messageEvent6 -> sameOrigin(event.getMessage(), messageEvent6.getMessage()), messageEvent6 -> {
                            if (messageEvent6.getMessage().getContentRaw().equals("null")) {
                                return;
                            }

                            try {
                                messageEvent6.getMessage().getMentionedChannels().get(0).sendMessage(builder.build()).queue();
                            } catch (IndexOutOfBoundsException ok) {
                                messageEvent6.getMessage().reply("Invalid channel.").queue();
                            }

                        })));
                    }));
                }));
            }));
        }));
    }

    public boolean sameOrigin(Message message1, Message message2) {
        if (!message1.getChannel().getId().equals(message2.getChannel().getId())) {
            return false;
        }

        return message1.getAuthor().getId().equals(message2.getAuthor().getId());
    }

}
