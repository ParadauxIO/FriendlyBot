/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.managers.AuditManager :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.managers;

import io.paradaux.friendlybot.utils.embeds.AuditLogEmbed;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.database.AuditLogEntry;
import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;

import java.util.Date;

public class AuditManager {

    // TODO tidy

    private static AuditManager instance = null;
    private final ConfigurationEntry config;
    private final Logger logger;

    public AuditManager(ConfigurationEntry config, Logger logger) {
        this.config = config;
        this.logger = logger;

        logger.info("Initialising: AuditLogController");
        instance = this;
    }

    public static AuditManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

    public void log(AuditLogEmbed.Action action, User target, User staff, String reason,
                    String incidentID) {
        AuditLogEmbed embed = new AuditLogEmbed(action, target, staff, reason, incidentID);

        AuditLogEntry auditLogEntry = new AuditLogEntry()
                .setAction(action)
                .setIncidentID(incidentID)
                .setUserTag(target.getAsTag())
                .setUserID(target.getId())
                .setReason(reason)
                .setTimestamp(new Date());

        MongoManager.getInstance().addAuditLog(auditLogEntry);

        TextChannel channel = DiscordBotManager.getInstance().getChannel(config.getPrivateAuditLogChannelId());
        embed.sendEmbed(channel);

    }



}

