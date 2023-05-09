package com.maayan.integrative_20.Entities;

import java.util.Arrays;

public class CalendarEntity {


    private String calendarID;
    private String[] events;

    public CalendarEntity() {
    }

    public CalendarEntity(String calendarID, String[] events) {
        this.calendarID = calendarID;
        this.events = events;
    }

    public String getCalendarID() {
        return calendarID;
    }

    public void setCalendarID(String calendarID) {
        this.calendarID = calendarID;
    }

    public String[] getEvents() {
        return events;
    }

    public void setEvents(String[] events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "calendarID='" + calendarID + '\'' +
                ", events=" + Arrays.toString(events) +
                '}';
    }
}
