package io.paradaux.friendlybot.managers;

import io.paradaux.friendlybot.core.utils.TimeUtils;
import io.paradaux.friendlybot.core.utils.embeds.moderation.BannedEmbed;
import io.paradaux.friendlybot.core.utils.models.database.BanEntry;
import io.paradaux.friendlybot.core.utils.models.database.GuildSettingsEntry;
import io.paradaux.friendlybot.core.utils.models.database.KickEntry;
import io.paradaux.friendlybot.core.utils.models.database.RescindmentEntry;
import io.paradaux.friendlybot.core.utils.models.database.TempBanEntry;
import io.paradaux.friendlybot.core.utils.models.database.WarningEntry;
import io.paradaux.friendlybot.core.utils.models.exceptions.ManagerNotReadyException;
import io.paradaux.friendlybot.core.utils.models.types.ModerationAction;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.Date;

public class PunishmentManager {

    private static PunishmentManager instance;
    private final GuildSettingsManager guilds;
    private final MongoManager mongo;

    public PunishmentManager() {
        this.guilds = GuildSettingsManager.getInstance();
        this.mongo = MongoManager.getInstance();
        instance = this;
    }

    public static PunishmentManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

    public void tempBanUser(Guild guild, Member target, Member staff, TextChannel channel, String time, String reason) {
        GuildSettingsEntry settings = guilds.getGuild(guild.getId());

        long period = TimeUtils.getTime(time);
        Date expiry = new Date(System.currentTimeMillis() + period);

        TempBanEntry entry = new TempBanEntry()
                .setIncidentId(String.valueOf(settings.getIncidentId()))
                .setUserTag(target.getUser().getAsTag())
                .setUserId(target.getId())
                .setStaffTag(staff.getUser().getAsTag())
                .setStaffId(staff.getId())
                .setReason(reason)
                .setTimestamp(new Date())
                .setExpiry(expiry);

        mongo.addTempBanEntry(entry);

        TextChannel publicAuditLog = DiscordBotManager.getInstance().getChannel(settings.getPublicAuditLogChannel());
        AuditManager.getInstance().log(ModerationAction.TEMP_BAN, target.getUser(), staff.getUser(), entry.getReason(), entry.getIncidentId());
        publicAuditLog.sendMessage(this.publicAuditPunishmentEmbed()).queue();

        target.getUser().openPrivateChannel().queue((privateChannel) -> {
            MessageEmbed banNotification = new EmbedBuilder()
                    .setColor(0xeb5132)
                    .setTitle("You have been temporarily banned.")
                    .setDescription("**Reason**: " + entry.getReason() + "\n**Period**: " + TimeUtils.millisecondsToDisplay(period))
                    .setFooter("Incident ID: " + entry.getIncidentId() + ". For more information, reach out to a member of the moderation team.")
                    .setTimestamp(new Date().toInstant())
                    .build();

            privateChannel.sendMessage(banNotification).queue((message2) -> {
                guild.ban(target, 0).queue();
            });

        });
    }

    public void banUser(Guild guild, Member target, Member staff, TextChannel channel, String reason) {
        GuildSettingsEntry settings = guilds.getGuild(guild.getId());

        String incidentID = String.valueOf(settings.getIncidentId());

        BanEntry entry = new BanEntry()
                .setIncidentID(incidentID)
                .setReason(reason)
                .setStaffID(staff.getId())
                .setStaffTag(staff.getUser().getAsTag())
                .setUserID(target.getId())
                .setUserTag(target.getUser().getAsTag());

        mongo.addBanEntry(entry);
        AuditManager.getInstance().log(ModerationAction.BAN, target.getUser(), staff.getUser(), reason, incidentID);

        BannedEmbed embed = new BannedEmbed(reason, incidentID);
        target.getUser().openPrivateChannel().queue((privateChannel) -> privateChannel.sendMessage(embed.getEmbed()).queue());

        guild.ban(target, 0).queue();
    }

    public void kickUser(Guild guild, Member target, Member staff, TextChannel channel, String reason) {
        GuildSettingsEntry settings = guilds.getGuild(guild.getId());

        MongoManager mongo = MongoManager.getInstance();

        String incidentID = String.valueOf(settings.getIncidentId());

        KickEntry entry = new KickEntry()
                .setIncidentID(incidentID)
                .setReason(reason)
                .setStaffID(staff.getId())
                .setStaffTag(staff.getUser().getAsTag())
                .setUserID(target.getId())
                .setUserTag(target.getUser().getAsTag());

        mongo.addKickEntry(entry);

        AuditManager.getInstance().log(ModerationAction.KICK, target.getUser(), staff.getUser(), reason, incidentID);

        DiscordBotManager.getInstance().getChannel(settings.getPublicAuditLogChannel()).sendMessage(this.publicAuditPunishmentEmbed()).queue();

        target.getUser().openPrivateChannel().queue((privateChannel ) -> {
            MessageEmbed kickNotification = new EmbedBuilder()
                    .setColor(0xffff99)
                    .setTitle("You have been kicked.")
                    .setDescription("**Reason**: " + reason)
                    .setFooter("Incident ID: " + incidentID + ". For more information, reach out to the moderation team via mod-mail.")
                    .setTimestamp(new Date().toInstant())
                    .build();

            privateChannel.sendMessage(kickNotification).queue();

            target.kick(reason).queue();
        });
    }

    public void warnUser(Guild guild, Member target, Member staff, TextChannel channel, String reason) {
        GuildSettingsEntry settings = guilds.getGuild(guild.getId());

        MongoManager mongo = MongoManager.getInstance();

        String incidentID = String.valueOf(settings.getIncidentId());

        WarningEntry entry = new WarningEntry()
                .setIncidentID(incidentID)
                .setReason(reason)
                .setStaffID(staff.getId())
                .setStaffTag(staff.getUser().getAsTag())
                .setUserID(target.getId())
                .setUserTag(target.getUser().getAsTag())
                .setTimestamp(new Date());

        mongo.addWarnEntry(entry);

        AuditManager.getInstance().log(ModerationAction.WARN, target.getUser(), staff.getUser(), reason, incidentID);

        DiscordBotManager.getInstance().getChannel(settings.getPublicAuditLogChannel()).sendMessage(this.publicAuditPunishmentEmbed()).queue();

        target.getUser().openPrivateChannel().queue((privateChannel) -> {
            MessageEmbed warnNotification = new EmbedBuilder()
                    .setColor(0x33cccc)
                    .setTitle("You have been warned.")
                    .setDescription("**Reason**: " + reason + "\n**N.B**: Receiving a second warning is an automatic temporary ban.")
                    .setFooter("Incident ID: " + incidentID + ". For more information, reach out to the moderation team via mod-mail.")
                    .setTimestamp(new Date().toInstant())
                    .build();

            channel.sendMessage(warnNotification).queue();
        });
    }

    private MessageEmbed sameChannelPunishmentEmbed() {
        // TODO
        return null;
    }

    private MessageEmbed privateAuditPunishmentEmbed() {
        // TODO
        return null;
    }

    private MessageEmbed publicAuditPunishmentEmbed() {
        //User target, ModerationAction action, String reason
//        return new EmbedBuilder()
//                .setColor(0xeb5132)
//                .setTitle(target.getUser().getAsTag() + " has been temporarily banned.")
//                .setDescription("**Reason**: " + entry.getReason() + "\n**Period**: " + TimeUtils.millisecondsToDisplay(period))
//                .setFooter("Incident ID: " + entry.getIncidentId() + ". For more information, reach out to the moderation team via mod-mail.")
//                .setTimestamp(new Date().toInstant())
//                .build();
        // TODO
        return null;
    }

    public void deleteWarning(Guild guild, Member staff, String warningId, String reason) {
        GuildSettingsEntry settings = guilds.getGuild(guild.getId());

        WarningEntry entry = mongo.getWarningEntry(warningId);
        mongo.deleteWarning(warningId);
        String incidentId = String.valueOf(settings.getIncidentId());

        RescindmentEntry rescindment = new RescindmentEntry()
                .setAction(ModerationAction.WARN)
                .setIncidentId(incidentId)
                .setIncidentIdOfPunishment(entry.getIncidentID())
                .setUserTag(entry.getUserTag())
                .setUserId(entry.getUserID())
                .setStaffTag(staff.getUser().getAsTag())
                .setStaffId(staff.getId())
                .setReason(reason)
                .setTimeOfPunishment(entry.getTimestamp())
                .setTimeOfRescindment(new Date());

        mongo.addRescindment(rescindment);

        MessageEmbed publicAudit = new EmbedBuilder()
                .setColor(0x34eb9b)
                .setTitle("Punishment Rescinded. | Warning of " + entry.getUserTag())
                .setDescription(rescindment.getReason())
                .setFooter("Punishment ID: " + entry.getIncidentID() + " Incident ID: " + incidentId)
                .setTimestamp(new Date().toInstant())
                .build();

        DiscordBotManager.getInstance().getChannel(settings.getPublicAuditLogChannel()).sendMessage(publicAudit).queue();

        User target = DiscordBotManager.getInstance().getUser(entry.getUserID());

        target.openPrivateChannel().queue((channel) -> {
            MessageEmbed rescindmentNotification = new EmbedBuilder()
                    .setColor(0x33cccc)
                    .setTitle("A warning levied against you has been taken back..")
                    .setDescription("** Original Reason**: " + entry.getReason() + "\n" + rescindment.getReason())
                    .setFooter("Punishment ID: " + entry.getIncidentID() + " Incident ID: " + incidentId)
                    .setTimestamp(new Date().toInstant())
                    .build();

            channel.sendMessage(rescindmentNotification).queue();
        });
    }
}
