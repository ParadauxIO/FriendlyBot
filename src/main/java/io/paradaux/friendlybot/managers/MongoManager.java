/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.managers.MongoManager :  31/01/2021, 01:26
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

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.database.*;
import io.paradaux.friendlybot.utils.models.enums.TicketStatus;
import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.slf4j.Logger;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import java.util.Date;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoManager {

    // Singleton Instance
    private static MongoManager instance;

    // Dependencies
    private final ConfigurationEntry config;
    private final Logger logger;
    private MongoClient client;

    private final MongoCollection<PendingVerificationEntry> pendingVerification;
    private final MongoCollection<VerificationEntry> verification;
    private final MongoCollection<CounterEntry> counterCollection;
    private final MongoCollection<AuditLogEntry> auditLog;
    private final MongoCollection<WarningEntry> warnings;
    private final MongoCollection<ModMailEntry> modmail;
    private final MongoCollection<BanEntry> bans;
    private final MongoCollection<KickEntry> kicks;
    private final MongoCollection<MessageEntry> loggedMessages;
    private final MongoCollection<MessageEntry> botBrain;
    private final MongoCollection<UserSettingsEntry> userSettings;
    private final MongoCollection<TempBanEntry> tempbans;
    private final MongoCollection<TagEntry> tags;
    private final MongoCollection<RescindmentEntry> rescindments;
    private final MongoCollection<GuildSettingsEntry> guilds;

    public MongoManager(ConfigurationEntry config, Logger logger) {
        this.config = config;
        this.logger = logger;

        logger.info("Initialising: Database Controller");
        ConnectionString connectionString = new ConnectionString(config.getMongoDbConnectionUri());

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
        loggedMessages = dataBase.getCollection("messages", MessageEntry.class);
        botBrain = dataBase.getCollection("ai_brain", MessageEntry.class);
        userSettings = dataBase.getCollection("user_settings", UserSettingsEntry.class);
        tempbans = dataBase.getCollection("tempbans", TempBanEntry.class);
        tags = dataBase.getCollection("tags", TagEntry.class);
        rescindments = dataBase.getCollection("rescindments", RescindmentEntry.class);
        guilds = dataBase.getCollection("guildsettings", GuildSettingsEntry.class);

        instance = this;
    }

    public static MongoManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

    public MongoCollection<GuildSettingsEntry> getGuildSettings() {
        return guilds;
    }

    public MongoCollection<UserSettingsEntry> getUserSettings() {
        return userSettings;
    }

    public MongoCollection<MessageEntry> getAiMessages() {
        return botBrain;
    }

    public MongoCollection<TagEntry> getTags() {
        return tags;
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

    public void setModMailStatus(String ticketNumber, TicketStatus status) {
        ModMailEntry entry = getModMailEntry(ticketNumber);
        entry.setStatus(status);
        updateModMailEntry(ticketNumber, entry);
    }

    public FindIterable<ModMailEntry> getModMailEntries(TicketStatus status) {
        return modmail.find(Filters.eq("status", status.toString()));
    }

    public void updateModMailEntry(String ticketNumber, ModMailEntry entry) {
        modmail.findOneAndReplace(Filters.eq("ticket_number", ticketNumber), entry);
    }

    public void addLoggedMessage(MessageEntry message) {
        loggedMessages.insertOne(message);
    }

    public MessageEntry getLoggedMessage(String messageId) {
        return loggedMessages.find(Filters.eq("message_id", messageId)).first();
    }

    public void updateLoggedMessage(String messageId, MessageEntry entry) {
        loggedMessages.findOneAndReplace(Filters.eq("message_id", messageId), entry);
    }

    public void addTempBanEntry(TempBanEntry entry) {
        tempbans.insertOne(entry);
    }

    public TempBanEntry getTempBanEntry(String discordId) {
        return tempbans.find(Filters.eq("discord_id", discordId)).first();
    }

    public FindIterable<TempBanEntry> getTempBans() {
        return tempbans.find();
    }

    public void deleteWarning(String incidentId) {
        warnings.findOneAndDelete(Filters.eq("incident_id", incidentId));
    }

    public RescindmentEntry getRescindment(String incidentId) {
        return rescindments.find(Filters.eq("incident_id", incidentId)).first();
    }

    public void addRescindment(RescindmentEntry entry) {
        rescindments.insertOne(entry);
    }

}
