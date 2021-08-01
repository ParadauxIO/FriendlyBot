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

package io.paradaux.friendlybot.discord.commands.staff.moderation;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.managers.PunishmentManager;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.PrivilegedCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;

public class TempBanCommand extends PrivilegedCommand {

    private final MongoManager mongo;
    private final PunishmentManager punishments;

    public TempBanCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager, MongoManager mongo) {
        super(config, logger, permissionManager);
        this.mongo = mongo;
        this.name = "tempban";
        this.help = "Temporarily bans a user.";
        this.punishments = PunishmentManager.getInstance();
    }

    @Override
    protected void execute(CommandEvent event) {

        Member staff = event.getMember();
        Message message = event.getMessage();
        String[] args = getArgs(event.getArgs());

        if (!isStaff(event.getGuild(), staff.getId())) {
            respondNoPermission(message, "[Moderator, Administrator]");
            return;
        }

        if (event.getArgs().isEmpty()) {
            respondSyntaxError(message, ";tempban <user> <time> <reason>");
            return;
        }

        Member target = retrieveMember(event.getGuild(), args[0]);

        if (target == null) {
            message.reply("User does not exist.").queue();
            return;
        }

        punishments.tempBanUser(event.getGuild(), target, event.getMember(), event.getTextChannel(),  args[1], parseSentance(2, args));
    }
}
