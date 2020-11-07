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

package io.paradaux.csbot.controllers;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import io.paradaux.csbot.api.ConfigurationCache;
import io.paradaux.csbot.models.*;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Date;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DatabaseController implements IController {

    final private static ConfigurationCache configurationCache = ConfigurationController.getConfigurationCache();
    final private static Logger logger = LogController.getLogger();
    public static DatabaseController INSTANCE;
    private static MongoClient client;
    private static MongoDatabase dataBase;

    private static MongoCollection<PendingVerificationEntry> pendingVerification;
    private static MongoCollection<AutomaticWarningEntry> automaticWarnings;
    private static MongoCollection<VerificationEntry> verification;
    private static MongoCollection<AuditLogEntry> auditLog;
    private static MongoCollection<WarningEntry> warnings;
    private static MongoCollection<BanEntry> bans;
    private static MongoCollection<KickEntry> kicks;

    @Override
    public void initialise() {
        ConnectionString connectionString = new ConnectionString(configurationCache.getMongoUri());
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        try {
            client = MongoClients.create(clientSettings);
        } catch (Exception exception) {
            logger.error("There was an error logging in to MongoDB", exception);
            return;
        }

        dataBase = client.getDatabase("csfriendlybot");

        pendingVerification = dataBase.getCollection("pendingVerification", PendingVerificationEntry.class);
        automaticWarnings = dataBase.getCollection("automatic-warnings", AutomaticWarningEntry.class);
        verification = dataBase.getCollection("verification", VerificationEntry.class);
        auditLog = dataBase.getCollection("auditlog", AuditLogEntry.class);
        warnings = dataBase.getCollection("warnings", WarningEntry.class);
        bans = dataBase.getCollection("bans", BanEntry.class);
        kicks = dataBase.getCollection("kicks", KickEntry.class);

        INSTANCE = this;

    }

    public void addPendingVerificationUser(String discordID, String guildID, String verificationCode) {
        PendingVerificationEntry pendingVerificationEntry = new PendingVerificationEntry()
                .setDiscordID(discordID)
                .setGuildID(guildID)
                .setVerificationCode(verificationCode);

        pendingVerification.insertOne(pendingVerificationEntry);
    }

    public void setVerifiedUser(String discordID, String guildID) {
        pendingVerification.findOneAndDelete(Filters.eq("discord_id", discordID));

        VerificationEntry verificationEntry = new VerificationEntry()
                .setDiscordID(discordID)
                .setGuildID(guildID)
                .setDateVerified(new Date());

        verification.insertOne(verificationEntry);
    }

    public boolean isPendingVerification(String discordID) {
        PendingVerificationEntry pendingVerificationEntry = pendingVerification.find(Filters.eq("discord_id", discordID)).first();
        return pendingVerificationEntry != null;
    }

    public boolean isVerified(String discordID) {
        VerificationEntry verificationEntry = verification.find(Filters.eq("discord_id", discordID)).first();
        return verificationEntry != null;
    }

    @Nullable
    public String getVerificationCode(String discordID) {
        PendingVerificationEntry pendingVerificationEntry = pendingVerification.find(Filters.eq("discord_id", discordID)).first();
        return pendingVerificationEntry != null ? pendingVerificationEntry.getVerificationCode() : null;
    }

    public void addWarning(String discordID, String issuerID, String reason) {
        WarningEntry warningEntry = new WarningEntry()
                .setDiscordID(discordID)
                .setIssuerID(issuerID)
                .setReason(reason)
                .setDateIssued(new Date());

        warnings.insertOne(warningEntry);
    }



}
