/*
 * Copyright (c) 2021 |  Rían Errity. GPLv3
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.friendlybot.commands.utility;

import com.jagrosh.jdautilities.command.CommandEvent;
import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.utils.embeds.command.UserInfoEmbed;
import io.paradaux.friendlybot.utils.models.StringUtils;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.objects.PrivilegedCommand;
import net.dv8tion.jda.api.entities.Member;
import org.slf4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

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
        String argument = getArguments();

        if (argument != null && isStaff(event.getAuthor().getId())) {
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

        String accountCreated = StringUtils.formatLocalDateTime.from(member.getUser().getTimeCreated()));
        String joinedServer = DATE_FORMAT.format(LocalDateTime.from(member.getTimeJoined()));
        String nickname = member.getNickname() != null ? member.getNickname() : "No Nickname.";
        String roles = member.getRoles().toString();

        UserInfoEmbed embed = new UserInfoEmbed(tag, avatarUrl, status, accountCreated, joinedServer, roles, nickname);
        embed.sendEmbed(event.getTextChannel());

        // TODO add a staff version which shows infractions.
    }
}
