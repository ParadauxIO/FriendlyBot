/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.staff.moderation.KickCommand :  31/01/2021, 01:26
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
import io.paradaux.friendlybot.FriendlyBot;
import io.paradaux.friendlybot.managers.AuditManager;
import io.paradaux.friendlybot.managers.DiscordBotManager;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.utils.embeds.AuditLogEmbed;
import io.paradaux.friendlybot.utils.embeds.moderation.KickedEmbed;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.database.KickEntry;
import io.paradaux.friendlybot.utils.models.types.ModerationAction;
import io.paradaux.friendlybot.utils.models.types.PrivilegedCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;

import java.util.Date;

/**
 * This is a command which kicks the specified user.
 *
 * @author Rían Errity
 * @version Last modified for 0.1.0-SNAPSHOT
 * @since 4/11/2020 DD/MM/YY
 * @see FriendlyBot
 * */

public class KickCommand extends PrivilegedCommand {

    public KickCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager) {
        super(config, logger, permissionManager);
        this.name = "kick";
        this.help = "Kicks the specified user";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();

        String[] args = getArgs(event.getArgs());
        String authorID = event.getAuthor().getId();

        if (!isStaff(authorID)) {
            respondNoPermission(message, "[Moderator, Administrator]");
            return;
        }
        if (args.length < 2) {
            respondSyntaxError(message, ";kick <userid/@mention> <reason>");
            return;
        }

        User target = parseTarget(message, args[0]);

        if (target == null) {
            message.getChannel().sendMessage("You did not specify a (valid) target.").queue();
            return;
        }

        if (isStaff(target.getId())) {
            message.getChannel().sendMessage("You cannot ban a staff member.").queue();
            return;
        }

        MongoManager mongo = MongoManager.getInstance();

        String incidentID = mongo.getNextIncidentID();
        String reason = parseSentance(1, args);

        KickEntry entry = new KickEntry()
                .setIncidentID(incidentID)
                .setReason(reason)
                .setStaffID(authorID)
                .setStaffTag(event.getAuthor().getAsTag())
                .setUserID(target.getId())
                .setUserTag(target.getAsTag());

        mongo.addKickEntry(entry);

        AuditManager.getInstance().log(ModerationAction.KICK, target,
                event.getAuthor(), reason, incidentID);

        MessageEmbed publicAudit = new EmbedBuilder()
                .setColor(0xffff99)
                .setTitle(target.getAsTag() + " has been kicked.")
                .setDescription("**Reason**: " + reason)
                .setFooter("Incident ID: " + incidentID + ". For more information, reach out to the moderation team via mod-mail.")
                .setTimestamp(new Date().toInstant())
                .build();

        DiscordBotManager.getInstance().getChannel(getConfig().getPublicAuditLogChannelId()).sendMessage(publicAudit).queue();
        message.getChannel().sendMessage(publicAudit).queue();

        target.openPrivateChannel().queue((channel) -> {
            MessageEmbed kickNotification = new EmbedBuilder()
                    .setColor(0xffff99)
                    .setTitle("You have been kicked.")
                    .setDescription("**Reason**: " + reason)
                    .setFooter("Incident ID: " + incidentID + ". For more information, reach out to the moderation team via mod-mail.")
                    .setTimestamp(new Date().toInstant())
                    .build();

            channel.sendMessage(kickNotification).queue();

            Member targetMember = retrieveMember(message.getGuild(), target);

            if (targetMember == null) {
                message.getChannel().sendMessage("The user specified is not a member of this discord")
                        .queue();
                return;
            }

            targetMember.kick(reason).queue();
        });

    }
}