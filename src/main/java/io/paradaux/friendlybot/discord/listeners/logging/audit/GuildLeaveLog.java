/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.listeners.logging.audit.GuildLeaveLog :  03/02/2021, 03:18
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

package io.paradaux.friendlybot.discord.listeners.logging.audit;

import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.database.GuildSettingsEntry;
import io.paradaux.friendlybot.core.utils.models.types.DiscordEventListener;
import io.paradaux.friendlybot.managers.DiscordBotManager;
import io.paradaux.friendlybot.managers.GuildSettingsManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class GuildLeaveLog extends DiscordEventListener {

    private final GuildSettingsManager guilds;
    public GuildLeaveLog(ConfigurationEntry config, Logger logger, GuildSettingsManager guilds) {
        super(config, logger);
        this.guilds = guilds;
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {

        GuildSettingsEntry guild = guilds.getGuild(event.getGuild().getId());

        if (guild.getPublicAuditLogChannel() == null || guild.getPublicAuditLogChannel().isEmpty()) {
            return;
        }

        var user = event.getUser();
        var embed = new EmbedBuilder()
                .setTitle(user.getAsTag() + " has left the guild.")
                .setColor(0xff5050)
                .build();

        DiscordBotManager.getInstance().getChannel(guild.getPublicAuditLogChannel()).sendMessage(embed).queue();

    }
}
