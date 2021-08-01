/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.utils.models.types.PrivilegedCommand :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.core.utils.models.types;

import io.paradaux.friendlybot.managers.PermissionManager;
import io.paradaux.friendlybot.core.utils.embeds.notices.NoPermissionEmbed;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.slf4j.Logger;

public abstract class PrivilegedCommand extends BaseCommand {

    public PrivilegedCommand(ConfigurationEntry config, Logger logger, PermissionManager permissionManager) {
        super(config, logger, permissionManager);
    }

    public PrivilegedCommand(ConfigurationEntry config, Logger logger) {
        super(config, logger);
    }

    public PrivilegedCommand(Logger logger) {
        super(logger);
    }

    public PrivilegedCommand(Logger logger, PermissionManager permissionManager) {
        super(logger, permissionManager);
    }

    /**
     * Returns true if the provided user is a member of staff.
     * */
    public boolean isStaff(Guild guild, String discordID) {
        PermissionManager permissionManager = getPermissionManager();

        if (permissionManager == null) {
            throw new RuntimeException("This command is not setup to be used with permission-based checks.");
        }

        return getPermissionManager().isAdmin(discordID)
                || permissionManager.isMod(discordID)
                || permissionManager.isTechnician(discordID)
                || isAdmin(guild, discordID);
    }

    public boolean isAdmin(Guild guild, String discordId) {
        Member member = retrieveMember(guild, discordId);

        if (member == null) {
            return false;
        }

        return member.getPermissions().contains(Permission.ADMINISTRATOR);
    }

    /**
     * Fills in the canned no permission embed.
     * */
    public void respondNoPermission(Message message, String requiredRole) {
        message.addReaction("\uD83D\uDEAB").queue();
        new NoPermissionEmbed(message.getAuthor(), this.name, requiredRole)
                .sendEmbed(message.getTextChannel());
    }

}
