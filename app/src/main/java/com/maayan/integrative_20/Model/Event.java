package com.maayan.integrative_20.Model;

import java.util.Arrays;

public class Event {

    private String[] participants;
    private String subject;
    private String content;
    private String startTime;
    private String endTime;
    private String date;
    private EventType type;
    private String internalObjectId;

    public Event() {
    }

    public Event(String[] participants, String subject, String content, String startTime, String endTime, String date, EventType type, String internalObjectId) {
        this.participants = participants;
        this.subject = subject;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.type = type;
        this.internalObjectId = internalObjectId;
    }

    public String getParticipants() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < participants.length; i++) {
            text.append(participants[i]);
            if (i != participants.length - 1) {
                text.append(" ");
            }
        }
        return text.toString();
    }

    public void setParticipants(String[] participants) {
        this.participants = participants;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
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

    public String getInternalObjectId() {
        return internalObjectId;
    }

    public void setInternalObjectId(String internalObjectId) {
        this.internalObjectId = internalObjectId;
    }

    @Override
    public String toString() {
        return "Event{" +
                "participants=" + Arrays.toString(participants) +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", date='" + date + '\'' +
                ", type=" + type +
                ", internalObjectId='" + internalObjectId + '\'' +
                '}';
    }

    public void setType(String type) {
        this.type = EventType.valueOf("" + type.toUpperCase());
    }
}
