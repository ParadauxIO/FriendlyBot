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

import io.paradaux.friendlybot.bot.command.Command;
import io.paradaux.friendlybot.bot.command.CommandBody;
import io.paradaux.friendlybot.bot.command.DiscordCommand;
import io.paradaux.friendlybot.core.data.database.models.FGuild;
import io.paradaux.friendlybot.core.utils.StringUtils;
import io.paradaux.friendlybot.core.utils.TimeUtils;
import io.paradaux.friendlybot.core.utils.embeds.command.UserInfoEmbed;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;

@Command(name = "userinfo", description = "Shows information about yourself or the specified user.", permission = "commands.userinfo", aliases = {"info", "ui"})
public class UserInfoCommand extends DiscordCommand {

    @Override
    public void execute(FGuild guild, CommandBody body) {
        Member member;
        String argument = body.getArgStr();

//        if (!argument.isEmpty() && isStaff(guild.getGuild(), body.getUser().getId())) {
//            member = retrieveMember(guild.getGuild(), parseTarget(body.getMessage(), body.getArgs()[0]));
//
//            if (member == null) {
//                syntaxError(body.getMessage());
//                return;
//            }
//        } else {
//            member = body.getMember();
//        }

//        String tag = member.getUser().getAsTag();
       // String avatarUrl = member.getUser().getAvatarUrl();

       // String status = StringUtils.toTitleCase(member.getOnlineStatus().toString());

       // String accountCreated = TimeUtils.formatTime(member.getUser().getTimeCreated());
     //   String joinedServer = TimeUtils.formatTime(member.getTimeJoined());
     //   String nickname = member.getNickname() != null ? member.getNickname() : "No Nickname.";

        //List<Role> roles = member.getRoles();
        StringBuilder builder = new StringBuilder();

        builder.append("[ ");
        int i = 0;
      //  for (; i < member.getRoles().size()-1; i++) {
        //    builder.append(member.getRoles().get(i).getName()).append(", ");
      //  }

        //builder.append(roles.get(i).getName()).append(" ]");

       // UserInfoEmbed embed = new UserInfoEmbed(tag, avatarUrl, status, accountCreated, joinedServer, builder.toString(), nickname);
      //  embed.sendEmbed(guild.getGuild().getJDA().getTextChannelById(body.getMessage().getChannel().getId()));

    }
}
