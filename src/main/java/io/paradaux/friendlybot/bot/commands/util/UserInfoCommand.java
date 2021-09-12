/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.commands.utility.UserInfoCommand :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.bot.commands.util;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.core.utils.StringUtils;
import io.paradaux.friendlybot.core.utils.TimeUtils;
import io.paradaux.friendlybot.core.utils.embeds.command.UserInfoEmbed;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.PrivilegedCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.slf4j.Logger;

import java.util.List;

public class UserInfoCommand extends PrivilegedCommand {

    public UserInfoCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager) {
        super(config, logger, permissionManager);
        this.name = "userinfo";
        this.aliases = new String[]{"info", "ui"};
        this.help = "Shows information about yourself or the specified user.";
    }

    @Override
    protected void execute(CommandEvent event) {
        Member member;
        String argument = event.getArgs();

        if (!argument.isEmpty() && isStaff(event.getGuild(), event.getAuthor().getId())) {
            member = retrieveMember(event.getGuild(), parseTarget(event.getMessage(), getArgs(argument)[0]));

            if (member == null) {
                respondSyntaxError(event.getMessage(), ";userinfo <user>");
                return;
            }
        } else {
            member = event.getMember();
        }

        String tag = member.getUser().getAsTag();
        String avatarUrl = member.getUser().getAvatarUrl();

        String status = StringUtils.toTitleCase(member.getOnlineStatus().toString());

        String accountCreated = TimeUtils.formatTime(member.getUser().getTimeCreated());
        String joinedServer = TimeUtils.formatTime(member.getTimeJoined());
        String nickname = member.getNickname() != null ? member.getNickname() : "No Nickname.";

        List<Role> roles = member.getRoles();
        StringBuilder builder = new StringBuilder();

        builder.append("[ ");
        int i = 0;
        for (; i < member.getRoles().size()-1; i++) {
            builder.append(member.getRoles().get(i).getName()).append(", ");
        }

        builder.append(roles.get(i).getName()).append(" ]");

        UserInfoEmbed embed = new UserInfoEmbed(tag, avatarUrl, status, accountCreated, joinedServer, builder.toString(), nickname);
        embed.sendEmbed(event.getTextChannel());

    }
}
