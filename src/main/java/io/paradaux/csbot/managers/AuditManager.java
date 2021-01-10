/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
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

package io.paradaux.csbot.managers;

import io.paradaux.csbot.embeds.AuditLogEmbed;
import io.paradaux.csbot.models.exceptions.ManagerNotReadyException;
import io.paradaux.csbot.models.automatic.AuditLogEntry;
import io.paradaux.csbot.models.interal.ConfigurationEntry;
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

        TextChannel channel = DiscordBotManager.getInstance().getChannel(config.getAuditLogChannelID());
        embed.sendEmbed(channel);

    }

}