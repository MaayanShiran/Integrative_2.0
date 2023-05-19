package com.maayan.integrative_20.Model;

import com.maayan.integrative_20.Boundaries.ObjectBoundary;

import java.util.Arrays;

public class CalendarBoundary {


   // private String calendarID;
    private EventBoundary[] events;

    public CalendarBoundary() {
    }

    public CalendarBoundary(String calendarID, EventBoundary[] events) {
     //   this.calendarID = calendarID;
        this.events = events;
    }
/*
   public String getCalendarID() {
        return calendarID;
    }

    public void setCalendarID(String calendarID) {
        this.calendarID = calendarID;
    }
 */


    public EventBoundary[] getEvents() {
        return events;
    }

    public void setEvents(EventBoundary[] events) {
        this.events = events;
    }

    public CalendarBoundary(EventBoundary[] events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "CalendarBoundary{" +
                "events=" + Arrays.toString(events) +
                '}';
    }

    /*
   @Override
    public String toString() {
        return "Calendar{" +
                "calendarID='" + calendarID + '\'' +
                ", events=" + Arrays.toString(events) +
                '}';
    }
 */

}
