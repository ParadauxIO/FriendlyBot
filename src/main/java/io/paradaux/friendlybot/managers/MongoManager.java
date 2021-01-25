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

package io.paradaux.friendlybot.managers;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.database.*;
import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.slf4j.Logger;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoManager {

    // Singleton Instance
    public static MongoManager instance;

    // Dependencies
    private final ConfigurationEntry config;
    private final Logger logger;
    private MongoClient client;

    private MongoCollection<PendingVerificationEntry> pendingVerification;
    private MongoCollection<VerificationEntry> verification;
    private MongoCollection<CounterEntry> counterCollection;
    private MongoCollection<AuditLogEntry> auditLog;
    private MongoCollection<WarningEntry> warnings;
    private MongoCollection<ModMailEntry> modmail;
    private MongoCollection<BanEntry> bans;
    private MongoCollection<KickEntry> kicks;

    public MongoManager(ConfigurationEntry config, Logger logger) {
        this.config = config;
        this.logger = logger;

        logger.info("Initialising: Database Controller");
        ConnectionString connectionString = new ConnectionString(config.getMongoConnectionUri());

        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider
                .builder()
                .automatic(true)
                .build());

        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings
                .getDefaultCodecRegistry(), pojoCodecRegistry);

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

        MongoDatabase dataBase = client.getDatabase("friendlybot");

        pendingVerification = dataBase.getCollection("pendingVerification", PendingVerificationEntry.class);
        verification = dataBase.getCollection("verification", VerificationEntry.class);
        counterCollection = dataBase.getCollection("counter", CounterEntry.class);
        auditLog = dataBase.getCollection("auditlog", AuditLogEntry.class);
        warnings = dataBase.getCollection("warnings", WarningEntry.class);
        modmail = dataBase.getCollection("modmail", ModMailEntry.class);
        bans = dataBase.getCollection("bans", BanEntry.class);
        kicks = dataBase.getCollection("kicks", KickEntry.class);

        instance = this;
    }

    public static MongoManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }


    public String getNextIncidentID() {
        CounterEntry result = incrementCounter("last_incident_id");
        return Long.toString(result.getLastIncidentID() + 1);
    }

    public String getNextTicketNumber() {
        CounterEntry result = incrementCounter("last_ticket_number");
        return Long.toString(result.getLastIncidentID() + 1);
    }

    @CheckReturnValue
    @Nullable
    public CounterEntry incrementCounter(String field) {
        Document query = new Document("_id", new ObjectId("5fb6895ba9dc2abc3eec8c4a"));

        Document increment = new Document(field, 1L);
        Document update = new Document("$inc", increment);

        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions()
                .returnDocument(ReturnDocument.AFTER)
                .upsert(true);

        CounterEntry result = counterCollection.findOneAndUpdate(query, update, options);

        if (result == null) {
            result = new CounterEntry()
                    .setLastIncidentID(0L)
                    .setLastTickerNumber(0L);
        }

        return result;
    }

    public void addPendingVerificationUser(String discordID, String guildID,
                                           String verificationCode) {
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

    @CheckReturnValue
    public boolean isPendingVerification(String discordID) {
        PendingVerificationEntry pendingVerificationEntry = pendingVerification
                .find(Filters.eq("discord_id", discordID)).first();
        return pendingVerificationEntry != null;
    }

    @CheckReturnValue
    public boolean isVerified(String discordID) {
        VerificationEntry verificationEntry = verification
                .find(Filters.eq("discord_id", discordID)).first();
        return verificationEntry != null;
    }

    @CheckReturnValue
    @Nullable
    public String getVerificationCode(String discordID) {
        PendingVerificationEntry pendingVerificationEntry = pendingVerification
                .find(Filters.eq("discord_id", discordID)).first();
        return pendingVerificationEntry != null ? pendingVerificationEntry
                .getVerificationCode() : null;
    }

    public void addAuditLog(AuditLogEntry auditLogEntry) {
        auditLog.insertOne(auditLogEntry);
    }

    public void addBanEntry(BanEntry entry) {
        bans.insertOne(entry);
    }

    public void addKickEntry(KickEntry entry) {
        kicks.insertOne(entry);
    }

    public void addWarnEntry(WarningEntry entry) {
        warnings.insertOne(entry);
    }

    public void addModMailEntry(ModMailEntry entry) {
        modmail.insertOne(entry);
    }

    @CheckReturnValue
    @Nullable
    public AuditLogEntry getAuditLogEntry(String incidentID) {
        return auditLog.find(Filters.eq("incident_id", incidentID)).first();
    }

    @CheckReturnValue
    @Nullable
    public BanEntry getBanEntry(String incidentID) {
        return bans.find(Filters.eq("incident_id", incidentID)).first();
    }

    @CheckReturnValue
    @Nullable
    public KickEntry getKickEntry(String incidentID) {
        return kicks.find(Filters.eq("incident_id", incidentID)).first();
    }

    @CheckReturnValue
    @Nullable
    public WarningEntry getWarningEntry(String incidentID) {
        return warnings.find(Filters.eq("incident_id", incidentID)).first();
    }

    @CheckReturnValue
    @Nullable
    public FindIterable<PendingVerificationEntry> getPendingUsers() {
        return pendingVerification.find();
    }

    public void addModMail(ModMailEntry entry) {
        modmail.insertOne(entry);
    }

    public ModMailEntry getModMailEntry(String ticketNumber) {
        return modmail.find(Filters.eq("ticket_number", ticketNumber)).first();
    }

    public void setModMailStatus(String ticketNumber, ModMailEntry.ModMailStatus status) {
        ModMailEntry entry = getModMailEntry(ticketNumber);
        entry.setStatus(status);
        updateModMailEntry(ticketNumber, entry);
    }

    public void addModMailResponse(String ticketNumber, ModMailResponse response) {
        ModMailEntry entry = getModMailEntry(ticketNumber);
        List<ModMailResponse> responses = entry.getResponses();
        responses.add(response);
        entry.setResponses(responses);
        updateModMailEntry(ticketNumber, entry);
    }

    public FindIterable<ModMailEntry> getModMailEntries(ModMailEntry.ModMailStatus status) {
        return modmail.find(Filters.eq("status", status));
    }

    public void updateModMailEntry(String ticketNumber, ModMailEntry entry) {
        modmail.findOneAndReplace(Filters.eq("ticket_number", ticketNumber), entry);
    }

}
