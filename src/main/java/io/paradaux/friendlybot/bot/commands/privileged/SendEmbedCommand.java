/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.staff.technician.SendEmbedCommand :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.bot.commands.privileged;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.core.utils.embeds.AuditLogEmbed;
import io.paradaux.friendlybot.core.utils.embeds.PoliticsRulesEmbed;
import io.paradaux.friendlybot.core.utils.embeds.RulesEmbed;
import io.paradaux.friendlybot.core.utils.embeds.moderation.*;
import io.paradaux.friendlybot.core.utils.embeds.modmail.ModMailSentEmbed;
import io.paradaux.friendlybot.core.utils.embeds.notices.*;
import io.paradaux.friendlybot.core.utils.embeds.roleselection.PoliticsOptionEmbed;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.interfaces.Embed;
import io.paradaux.friendlybot.core.utils.models.types.ModerationAction;
import io.paradaux.friendlybot.core.utils.models.types.PrivilegedCommand;
import io.paradaux.friendlybot.managers.PermissionManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.slf4j.Logger;

@Command(name = "", description = "", permission = "", aliases = {})
public class SendEmbedCommand extends PrivilegedCommand {

    public SendEmbedCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager) {
        super(config, logger, permissionManager);
        this.name = "sendembed";
        this.aliases = new String[]{"se"};
        this.help = "Sends the specified embed.";
    }

    @Override
    protected void execute(CommandEvent event) {
        Message message = event.getMessage();

        String[] args = getArgs(event.getArgs());
        String authorID = event.getAuthor().getId();

        if (!getPermissionManager().isTechnician(authorID)) {
            respondNoPermission(message, "[Technician]");
            return;
        }

        if (args.length == 0) {
            respondSyntaxError(message, ";sendembed <embed>");
            return;
        }

        Embed embed;

        switch (args[0].toLowerCase()) {
            case "nopermission": {
                embed = new NoPermissionEmbed(event.getAuthor(), args[2], args[3]);
                break;
            }

            case "syntaxerror": {
                embed = new SyntaxErrorEmbed(event.getAuthor(), args[2], args[3]);
                break;
            }

            case "banned": {
                embed = new BannedEmbed(args[1], args[2]);
                break;
            }

            case "chatfiltertriggered": {
                embed = new ChatFilterTriggeredEmbed(ModerationAction.valueOf(args[1]),
                        args[3], args[4], args[5]);
                break;
            }

            case "citerule": {
                embed = new CiteRuleEmbed(args[1]);
                break;
            }

            case "kicked": {
                embed = new KickedEmbed(args[1], args[2]);
                break;
            }

            case "timedout": {
                embed = new TimedOutEmbed(args[1], args[2]);
                break;
            }

            case "warning": {
                embed = new WarningEmbed(args[1], args[2]);
                break;
            }

            case "modmailsent": {
                embed = new ModMailSentEmbed(args[1], args[2]);
                break;
            }

            case "mdomailnotice": {
                embed = new ModMailNoticeEmbed();
                break;
            }

            case "rulesacceptancenotice": {
                embed = new RulesAcceptanceNoticeEmbed();
                break;
            }

            case "verificationnotice": {
                embed = new VerificationNoticeEmbed();
                break;
            }

            case "auditlog": {
                embed = new AuditLogEmbed(ModerationAction.valueOf(args[1]), event.getAuthor(),
                        args[2], args[3]);
                break;
            }

            case "rules": {
                embed = new RulesEmbed();
                break;
            }

            case "politicsrules": {
                embed = new PoliticsRulesEmbed();
                break;
            }

            case "politicsoption": {
                embed = new PoliticsOptionEmbed();
                break;
            }

            default: {
                respondSyntaxError(message, ";sendembed <embed>");
                return;
            }
        }

        embed.sendEmbed((TextChannel) event.getMessage().getChannel());
    }
}
