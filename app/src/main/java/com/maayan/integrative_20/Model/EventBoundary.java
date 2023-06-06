package com.maayan.integrative_20.Model;

import com.maayan.integrative_20.Boundaries.UserId;

import java.util.Arrays;

public class EventBoundary {

    private String eventId;
    private String subject;
    private String startTime;
    private String endTime;
    private UserId[] participants;
    private EventType type;

    public EventBoundary() {
    }

    public EventBoundary(String eventId, String subject, String startTime, String endTime, UserId[] participants, EventType type) {
        this.eventId = eventId;
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participants = participants;
        this.type = type;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public UserId[] getParticipants() {
        return participants;
    }

    public void setParticipants(UserId[] participants) {
        this.participants = participants;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EventBoundary{" +
                "eventId='" + eventId + '\'' +
                ", subject='" + subject + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", participants=" + Arrays.toString(participants) +
                ", type=" + type +
                '}';
    }
}
