package io.paradaux.friendlybot.utils.models.database;

import io.paradaux.friendlybot.utils.models.types.ModerationAction;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.Date;

public class RescindmentEntry implements Serializable {

    protected static final long serialVersionUID = 1L;

    @BsonProperty(value = "action")
    private ModerationAction action;

    @BsonProperty(value = "incident_id")
    private String incidentId;

    @BsonProperty(value = "punishment_incident_id")
    private String incidentIdOfPunishment;

    @BsonProperty(value = "user_tag")
    private String userTag;

    @BsonProperty(value = "user_id")
    private String userId;

    @BsonProperty(value = "staff_tag")
    private String staffTag;

    @BsonProperty(value = "staff_id")
    private String staffId;

    @BsonProperty(value = "reason")
    private String reason;

    @BsonProperty(value = "punishment_time")
    private Date timeOfPunishment;

    @BsonProperty(value = "timestamp")
    private Date timeOfRescindment;
    
    public RescindmentEntry() {
        
    }

    public RescindmentEntry(ModerationAction action, String incidentId, String incidentIdOfPunishment, String userTag, String userId,
                            String staffTag, String staffId, String reason, Date timeOfPunishment, Date timeOfRescindment) {
        this.action = action;
        this.incidentId = incidentId;
        this.incidentIdOfPunishment = incidentIdOfPunishment;
        this.userTag = userTag;
        this.userId = userId;
        this.staffTag = staffTag;
        this.staffId = staffId;
        this.reason = reason;
        this.timeOfPunishment = timeOfPunishment;
        this.timeOfRescindment = timeOfRescindment;
    }

    public ModerationAction getAction() {
        return action;
    }

    public String getIncidentId() {
        return incidentId;
    }

    public String getIncidentIdOfPunishment() {
        return incidentIdOfPunishment;
    }

    public String getUserTag() {
        return userTag;
    }

    public String getUserId() {
        return userId;
    }

    public String getStaffTag() {
        return staffTag;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getReason() {
        return reason;
    }

    public Date getTimeOfPunishment() {
        return timeOfPunishment;
    }

    public Date getTimeOfRescindment() {
        return timeOfRescindment;
    }

    public RescindmentEntry setAction(ModerationAction action) {
        this.action = action;
        return this;
    }

    public RescindmentEntry setIncidentId(String incidentId) {
        this.incidentId = incidentId;
        return this;
    }

    public RescindmentEntry setIncidentIdOfPunishment(String incidentIdOfPunishment) {
        this.incidentIdOfPunishment = incidentIdOfPunishment;
        return this;
    }

    public RescindmentEntry setUserTag(String userTag) {
        this.userTag = userTag;
        return this;
    }

    public RescindmentEntry setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public RescindmentEntry setStaffTag(String staffTag) {
        this.staffTag = staffTag;
        return this;
    }

    public RescindmentEntry setStaffId(String staffId) {
        this.staffId = staffId;
        return this;
    }

    public RescindmentEntry setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public RescindmentEntry setTimeOfPunishment(Date timeOfPunishment) {
        this.timeOfPunishment = timeOfPunishment;
        return this;
    }

    public RescindmentEntry setTimeOfRescindment(Date timeOfRescindment) {
        this.timeOfRescindment = timeOfRescindment;
        return this;
    }
}
