/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.managers.VerificationManager :  31/01/2021, 01:26
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

import com.mongodb.client.FindIterable;
import io.paradaux.friendlybot.core.utils.StringUtils;
import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.database.PendingVerificationEntry;
import io.paradaux.friendlybot.core.utils.models.exceptions.ManagerNotReadyException;
import io.paradaux.friendlybot.core.utils.models.exceptions.VerificationException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.concurrent.ExecutionException;

public class VerificationManager {

    public static VerificationManager instance;
    private final Logger logger;
    private final MongoManager mongo;
    private final ConfigurationEntry config;
    private final MailGunManager mailGun;

    public VerificationManager(ConfigurationEntry config, Logger logger, MongoManager mongo, MailGunManager mailGun) {
        this.config = config;
        this.logger = logger;
        this.mongo = mongo;
        this.mailGun = mailGun;

        logger.info("Initialising: Verification Manager");
        instance = this;
    }

    public static VerificationManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

    public boolean setVerified(String discordId, String guildId, @Nullable String verificationCode) throws VerificationException {
        if (!(verificationCode == null)) {
            if (!verificationCode.equals(mongo.getVerificationCode(discordId))) {
                return false;
            }
        }

        mongo.setVerifiedUser(discordId, guildId);
        Guild guild = DiscordBotManager.getInstance().getGuild(guildId);
        Role role = DiscordBotManager.getInstance().getRole(guildId, config.getVerifiedRoleId());

        Member member;

        try {
            member = guild.retrieveMemberById(discordId).submit().get();
            guild.addRoleToMember(member, role).queue();
            mongo.setVerifiedUser(discordId, guildId);
        } catch (InterruptedException | ExecutionException exception) {
            logger.warn("Verification ran into a concurrency issue for ID: {} in Guild: {}", discordId, guildId);
            throw new VerificationException("Error execution asynchronously.");
        }

        logger.info("User: {} is now verified.", member.getUser().getAsTag());
        return true;
    }

    public void setPendingVerification(String email, String guildID, String discordID) throws VerificationException {
        DiscordBotManager discordBotManager = DiscordBotManager.getInstance();
        String verificationCode = StringUtils.generateVerificationCode();

        User user = discordBotManager.getUser(discordID);

        logger.info("Set {} as pending verification. Sending them an email.", user.getAsTag());

        mailGun.sendEmail(email, user.getAsTag(), verificationCode);
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
