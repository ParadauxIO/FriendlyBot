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

import com.mongodb.client.FindIterable;
import io.paradaux.csbot.models.automatic.PendingVerificationEntry;
import io.paradaux.csbot.models.exceptions.ManagerNotReadyException;
import io.paradaux.csbot.models.exceptions.VerificationException;
import io.paradaux.csbot.models.interal.ConfigurationEntry;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.mail.MessagingException;
import java.util.concurrent.ExecutionException;

public class VerificationManager {

    public static VerificationManager instance;
    private final Logger logger;
    private final MongoManager mongo;
    private final ConfigurationEntry config;

    public VerificationManager(ConfigurationEntry config, Logger logger, MongoManager mongo) {
        this.config = config;
        this.logger = logger;
        this.mongo = mongo;

        logger.info("Initialising: VerificationSystemController");

        instance = this;
    }

    public static VerificationManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

    public boolean setVerified(String discordID, String guildId, @Nullable String verificationCode) throws VerificationException {
        if (!(verificationCode == null)) {
            if (!verificationCode.equals(mongo.getVerificationCode(discordID))) {
                return false;
            }
        }

        mongo.setVerifiedUser(discordID, guildId);

        Guild guild = DiscordBotManager.getInstance().getGuild(guildId);

        Role role = DiscordBotManager.getInstance().getRole(guildId, config.getVerifiedRoleID());

        try {
            Member member = guild.retrieveMemberById(discordID).submit().get();
            guild.addRoleToMember(member, role).queue();
            mongo.setVerifiedUser(discordID, guildId);
        } catch (InterruptedException | ExecutionException exception) {
            throw new VerificationException("Error execution asynchronously.");
        }
        return true;
    }

    public void setPendingVerification(String email, String guildID, String discordID) throws VerificationException {
        DiscordBotManager discordBotManager = DiscordBotManager.getInstance();
        String verificationCode = SMTPManager.generateVerificationCode();

        Guild guild = discordBotManager.getGuild(guildID);
        User user = discordBotManager.getUser(discordID);

        try {
            SMTPManager.getInstance().sendVerificationEmail(email, verificationCode, user.getAsTag());
        } catch (MessagingException e) {
            throw new VerificationException("Error sending email.");
        }

        mongo.addPendingVerificationUser(discordID, guildID, verificationCode);
    }

    public StringBuilder getPendingUsers() {
        DiscordBotManager discordBotManager = DiscordBotManager.getInstance();
        StringBuilder builder = new StringBuilder();

        FindIterable<PendingVerificationEntry> entries = MongoManager.getInstance().getPendingUsers();

        if (entries == null) {
            return builder;
        }

        for (PendingVerificationEntry entry : entries) {
            User user = discordBotManager.getUser(entry.getDiscordID());
            Guild guild = discordBotManager.getGuild(entry.getGuildID());

            builder.append(String.format("%s : %s @ %s %n", user.getAsTag(),
                    entry.getVerificationCode(), guild.getName()));
        }

        return builder;
    }

}
