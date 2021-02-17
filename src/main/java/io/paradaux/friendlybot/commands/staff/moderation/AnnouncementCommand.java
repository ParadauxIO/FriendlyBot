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

import java.util.regex.Pattern;

public class AnnouncementCommand extends PrivilegedCommand {

    private static final Pattern URL_PATTERN = Pattern.compile("^(http://|https://)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?");
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

        event.getChannel().sendMessage("Please set the title.").queue(message -> waiter.waitForEvent(MessageReceivedEvent.class,
                messageEvent -> sameOrigin(event.getMessage(), messageEvent.getMessage()), messageEvent -> {
            final String title = messageEvent.getMessage().getContentRaw();

            event.getChannel().sendMessage("Please set the content.").queue(message2 -> waiter.waitForEvent(MessageReceivedEvent.class, messageEvent2 -> sameOrigin(event.getMessage(), messageEvent2.getMessage()), messageEvent2 -> {
                final String content = messageEvent2.getMessage().getContentRaw();

                event.getChannel().sendMessage("Please set the picture (use a link!)").queue(message3 -> waiter.waitForEvent(MessageReceivedEvent.class, messageEvent3 -> sameOrigin(event.getMessage(), messageEvent3.getMessage()), messageEvent3 -> {
                    final String imageUrl = messageEvent3.getMessage().getContentRaw();

                    if (!URL_PATTERN.matcher(messageEvent3.getMessage().getContentRaw()).find()) {
                        event.getChannel().sendMessage("Please use a *valid* link.").queue();
                        return;
                    }

                    event.getChannel().sendMessage("Please set the link").queue(message4 -> waiter.waitForEvent(MessageReceivedEvent.class, messageEvent4 -> sameOrigin(event.getMessage(), messageEvent4.getMessage()), messageEvent4 -> {
                        final String link = messageEvent4.getMessage().getContentRaw();

                        if (!URL_PATTERN.matcher(link).find()) {
                            event.getChannel().sendMessage("Please use a *valid* link.").queue();
                            return;
                        }

                        builder.setAuthor(title, link, imageUrl).setThumbnail(imageUrl).setDescription(content);

                        event.getChannel().sendMessage("Please set a color (use a HEX Integer with no prefix.)").queue(message5 -> waiter.waitForEvent(MessageReceivedEvent.class, messageEvent5 -> sameOrigin(event.getMessage(), messageEvent5.getMessage()), messageEvent5 -> {
                            final String colorString = messageEvent5.getMessage().getContentRaw();
                            final int color;

                            try {
                                color = Integer.parseInt(colorString, 16);
                            } catch (NumberFormatException ok) {
                                event.getChannel().sendMessage("You entered an invalid color.").queue();
                                return;
                            }

                            builder.setColor(color);

                            message.getChannel().sendMessage(builder.build()).queue(nullMessage -> event.getChannel().sendMessage("Where "
                                    + "should I send this? (type null for nowhere)").queue(message6 -> waiter.waitForEvent(MessageReceivedEvent.class, messageEvent6 -> sameOrigin(event.getMessage(), messageEvent6.getMessage()), messageEvent6 -> {
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
        }));
    }

    public boolean sameOrigin(Message message1, Message message2) {
        if (!message1.getChannel().getId().equals(message2.getChannel().getId())) {
            return false;
        }

        return message1.getAuthor().getId().equals(message2.getAuthor().getId());
    }

}
