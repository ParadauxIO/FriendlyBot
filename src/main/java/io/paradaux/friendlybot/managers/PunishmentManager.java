package io.paradaux.friendlybot.managers;

import io.paradaux.friendlybot.utils.TimeUtils;
import io.paradaux.friendlybot.utils.models.database.GuildSettingsEntry;
import io.paradaux.friendlybot.utils.models.database.TempBanEntry;
import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import io.paradaux.friendlybot.utils.models.types.ModerationAction;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

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

    public void tempBanUser(Guild guild, Member target, Member staff, String time, String reason) {
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

        MessageEmbed publicAudit = new EmbedBuilder()
                .setColor(0xeb5132)
                .setTitle(target.getAsTag() + " has been temporarily banned.")
                .setDescription("**Reason**: " + entry.getReason() + "\n**Period**: " + TimeUtils.millisecondsToDisplay(period))
                .setFooter("Incident ID: " + entry.getIncidentId() + ". For more information, reach out to the moderation team via mod-mail.")
                .setTimestamp(new Date().toInstant())
                .build();

        TextChannel channel = DiscordBotManager.getInstance().getChannel(getConfig().getPublicAuditLogChannelId());
        channel.sendMessage(publicAudit).queue();
        event.getChannel().sendMessage(publicAudit).queue();
        AuditManager.getInstance().log(ModerationAction.TEMP_BAN, target, staff, entry.getReason(), entry.getIncidentId());

        target.openPrivateChannel().queue((privateChannel) -> {
            MessageEmbed banNotification = new EmbedBuilder()
                    .setColor(0xeb5132)
                    .setTitle("You have been temporarily banned.")
                    .setDescription("**Reason**: " + entry.getReason() + "\n**Period**: " + TimeUtils.millisecondsToDisplay(period))
                    .setFooter("Incident ID: " + entry.getIncidentId() + ". For more information, reach out to a member of the moderation team.")
                    .setTimestamp(new Date().toInstant())
                    .build();

            privateChannel.sendMessage(banNotification).queue((message2) -> {
                message.getGuild().ban(target, 0).queue();
            });

        });
    }

    public void banUser(Guild guild, Member user, String reason) {
        GuildSettingsEntry settings = guilds.getGuild(guild.getId());
    }

    public void kickUser(Guild guild, Member user, String reason) {
        GuildSettingsEntry settings = guilds.getGuild(guild.getId());
    }

    public void warnUser(Guild guild, Member user, String reason) {
        GuildSettingsEntry settings = guilds.getGuild(guild.getId());
    }

}
