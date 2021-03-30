package io.paradaux.friendlybot.commands.staff.moderation;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.managers.DiscordBotManager;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.database.RescindmentEntry;
import io.paradaux.friendlybot.utils.models.database.WarningEntry;
import io.paradaux.friendlybot.utils.models.types.ModerationAction;
import io.paradaux.friendlybot.utils.models.types.PrivilegedCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;

import java.util.Date;

public class PunishmentCommand extends PrivilegedCommand {

    private final MongoManager mongo;

    public PunishmentCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager, MongoManager mongo) {
        super(config, logger, permissionManager);
        this.mongo = mongo;
        this.name = "punishment";
        this.help = "Utility for managing previously logged infractions.";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();
        String[] args = getArgs(event.getArgs());

        if (!isStaff(event.getGuild(), event.getAuthor().getId())) {
            respondNoPermission(message, "[Moderator, Administrator]");
            return;
        }

        if (args.length < 3) {
            respondSyntaxError(message, ";punishment <delwarn> <incidentid> <reason>");
            return;
        }

        switch (args[0]) {

            case "delwarn": {
                WarningEntry entry = mongo.getWarningEntry(args[1]);

                if (entry == null) {
                    message.reply("This warning does not exist.");
                    return;
                }

                mongo.deleteWarning(args[1]);
                String incidentId = mongo.getNextIncidentID();

                RescindmentEntry rescindment = new RescindmentEntry()
                        .setAction(ModerationAction.WARN)
                        .setIncidentId(incidentId)
                        .setIncidentIdOfPunishment(entry.getIncidentID())
                        .setUserTag(entry.getUserTag())
                        .setUserId(entry.getUserID())
                        .setStaffTag(message.getAuthor().getAsTag())
                        .setStaffId(message.getAuthor().getId())
                        .setReason(parseSentance(2, args))
                        .setTimeOfPunishment(entry.getTimestamp())
                        .setTimeOfRescindment(new Date());

                mongo.addRescindment(rescindment);

                MessageEmbed publicAudit = new EmbedBuilder()
                        .setColor(0x34eb9b)
                        .setTitle("Punishment Rescinded. | Warning of " + entry.getUserTag())
                        .setDescription(rescindment.getReason())
                        .setFooter("Punishment ID: " + args[1] + " Incident ID: " + incidentId)
                        .setTimestamp(new Date().toInstant())
                        .build();

                DiscordBotManager.getInstance().getChannel(getConfig().getPublicAuditLogChannelId()).sendMessage(publicAudit).queue();
                message.getChannel().sendMessage(publicAudit).queue();

                User target = DiscordBotManager.getInstance().getUser(entry.getUserID());

                target.openPrivateChannel().queue((channel) -> {
                    MessageEmbed rescindmentNotification = new EmbedBuilder()
                            .setColor(0x33cccc)
                            .setTitle("A warning levied against you has been taken back..")
                            .setDescription("** Original Reason**: " + entry.getReason() + "\n" + rescindment.getReason())
                            .setFooter("Punishment ID: " + args[1] + " Incident ID: " + incidentId)
                            .setTimestamp(new Date().toInstant())
                            .build();

                    channel.sendMessage(rescindmentNotification).queue();
                });
            }

        }

    }
}
