/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.staff.moderation.TempBanCommand :  06/02/2021, 18:10
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.paradaux.friendlybot.commands.staff.moderation;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.managers.AuditManager;
import io.paradaux.friendlybot.managers.DiscordBotManager;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.utils.TimeUtils;
import io.paradaux.friendlybot.utils.embeds.AuditLogEmbed;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.database.TempBanEntry;
import io.paradaux.friendlybot.utils.models.types.PrivilegedCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;

import java.util.Date;

public class TempBanCommand extends PrivilegedCommand {

    private final MongoManager mongo;

    public TempBanCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager, MongoManager mongo) {
        super(config, logger, permissionManager);
        this.mongo = mongo;
        this.name = "tempban";
        this.help = "Temporarily bans a user.";
    }

    @Override
    protected void execute(CommandEvent event) {

        User staff = event.getAuthor();
        Message message = event.getMessage();
        String[] args = getArgs(event.getArgs());

        if (!isStaff(staff.getId())) {
            respondNoPermission(message, "[Moderator, Administrator]");
            return;
        }

        if (args.length == 0) {
            respondSyntaxError(message, ";tempban <user> <time> <reason>");
            return;
        }

        User target = parseTarget(message, args[0]);

        if (target == null) {
            message.reply("User does not exist.").queue();
            return;
        }

        long period = TimeUtils.getTime(args[1]);
        Date expiry = new Date(System.currentTimeMillis() + period);

        TempBanEntry entry = new TempBanEntry()
                .setIncidentId(mongo.getNextIncidentID())
                .setUserTag(target.getAsTag())
                .setUserId(target.getId())
                .setStaffTag(staff.getAsTag())
                .setStaffId(staff.getId())
                .setReason(parseSentance(2, args))
                .setTimestamp(new Date())
                .setExpiry(expiry);

        mongo.addTempBanEntry(entry);

        MessageEmbed publicAudit = new EmbedBuilder()
                .setColor(0xeb5132)
                .setTitle(target.getAsTag() + " has been temporarily banned.")
                .setDescription("**Reason**: " + entry.getReason() + "\n**Period**: " + TimeUtils.millisecondsToDisplay(period))
                .setFooter("For more information, reach out to the moderation team via mod-mail.")
                .setTimestamp(new Date().toInstant())
                .build();

        TextChannel channel = DiscordBotManager.getInstance().getChannel(getConfig().getPublicAuditLogChannelId());
        channel.sendMessage(publicAudit).queue();
        event.getChannel().sendMessage(publicAudit).queue();
        AuditManager.getInstance().log(AuditLogEmbed.Action.TEMP_BAN, target, staff, entry.getReason(), entry.getIncidentId());

        target.openPrivateChannel().queue((privateChannel) -> {
            MessageEmbed banNotification = new EmbedBuilder()
                    .setColor(0xeb5132)
                    .setTitle("You have been temporarily banned.")
                    .setDescription("**Reason**: " + entry.getReason() + "\n**Period**: " + TimeUtils.millisecondsToDisplay(period))
                    .setFooter("For more information, reach out to a member of the moderation team.")
                    .setTimestamp(new Date().toInstant())
                    .build();

            privateChannel.sendMessage(banNotification).queue((message2) -> {
                message.getGuild().ban(target, 0).queue();
            });

        });

    }
}
