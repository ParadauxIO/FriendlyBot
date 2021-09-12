/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.staff.moderation.BanCommand :  31/01/2021, 01:26
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
import io.paradaux.friendlybot.FriendlyBot;
import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.PrivilegedCommand;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.managers.PunishmentManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;

/**
 * This is a command which bans the specified user.
 *
 * @author Rían Errity
 * @version Last modified for 0.1.0-SNAPSHOT
 * @since 4/11/2020 DD/MM/YY
 * @see FriendlyBot
 * */

@Command(name = "", description = "", permission = "", aliases = {})
public class BanCommand extends PrivilegedCommand {

    private final PunishmentManager punishments;

    public BanCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager) {
        super(config, logger, permissionManager);
        this.name = "ban";
        this.help = "Bans the specified user";
        this.punishments = PunishmentManager.getInstance();
    }

    @Override
    protected void execute(CommandEvent event) {
        MongoManager mongo = MongoManager.getInstance();
        Message message = event.getMessage();

        String[] args = getArgs(event.getArgs());
        String authorID = event.getAuthor().getId();

        if (!isStaff(event.getGuild(), authorID)) {
            respondNoPermission(message, "[Moderator, Administrator]");
            return;
        }

        if (args.length < 2) {
            respondSyntaxError(message, ";ban <userid/@mention> <reason>");
            return;
        }

        Member target = retrieveMember(event.getGuild(), args[0]);

        if (target == null) {
            message.getChannel().sendMessage("You did not specify a (valid) target.").queue();
            return;
        }

        if (isStaff(event.getGuild(), target.getId())) {
            message.getChannel().sendMessage("You cannot ban a staff member.").queue();
            return;
        }

        punishments.banUser(event.getGuild(), target, event.getMember(), event.getTextChannel(), parseSentance(1, args));
    }
}
