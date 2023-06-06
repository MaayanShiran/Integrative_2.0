package com.maayan.integrative_20.Model;

import com.maayan.integrative_20.Boundaries.UserId;

import java.util.ArrayList;
import java.util.Arrays;

public class CurrentUser {

    private static CurrentUser instance;

    private String chosenAvatar;
    private String chosenRole;
    private String chosenUsername;
    private UserId userId;
    private ArrayList<Event> events;
    private ArrayList<Event> eventsAfterSearch;
    private Task[] tasks;
    private String dateSelected;

    public static void setInstance(CurrentUser instance) {
        CurrentUser.instance = instance;
    }

    public String getDateSelected() {
        return dateSelected;
    }

    public void setDateSelected(String dateSelected) {
        this.dateSelected = dateSelected;
    }

    public CurrentUser() {
        this.events = new ArrayList<>();
        this.eventsAfterSearch = new ArrayList<>();
    }

    public static synchronized CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public CurrentUser(String chosenAvatar, String chosenRole, String chosenUsername, UserId userId, ArrayList<Event> events, Task[] tasks) {
        this.chosenAvatar = chosenAvatar;
        this.chosenRole = chosenRole;
        this.chosenUsername = chosenUsername;
        this.userId = userId;
        this.events = new ArrayList<>();
        this.tasks = tasks;
    }


    public ArrayList<Event> getEventsAfterSearch() {
        return eventsAfterSearch;
    }

    public void setEventsAfterSearch(ArrayList<Event> eventsAfterSearch) {
        this.eventsAfterSearch = eventsAfterSearch;
    }

    public String getChosenAvatar() {
        return chosenAvatar;
    }

    public void setChosenAvatar(String chosenAvatar) {
        this.chosenAvatar = chosenAvatar;
    }

    public String getChosenRole() {
        return chosenRole;
    }

    public void setChosenRole(String chosenRole) {
        this.chosenRole = chosenRole;
    }

    public String getChosenUsername() {
        return chosenUsername;
    }

    public void setChosenUsername(String chosenUsername) {
        this.chosenUsername = chosenUsername;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public Task[] getTasks() {
        return tasks;
    }

    public void setTasks(Task[] tasks) {
        this.tasks = tasks;
    }

    public Event getEventPositionByDate(String date) {//
        return null;
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "chosenAvatar='" + chosenAvatar + '\'' +
                ", chosenRole='" + chosenRole + '\'' +
                ", chosenUsername='" + chosenUsername + '\'' +
                ", userId=" + userId +
                ", events=" + events +
                ", tasks=" + Arrays.toString(tasks) +
                '}';
    }

    public void setNewEventDetails(String key, Object value) {
        switch (key) {
            case "subject":
                events.get(events.size() - 1).setSubject(value.toString());
                break;
            case "endTime":
                events.get(events.size() - 1).setEndTime(value.toString());
                break;
            case "startTime":
                events.get(events.size() - 1).setStartTime(value.toString());
                break;
            case "type":
                events.get(events.size() - 1).setType(value.toString());
                break;
            case "participants":
                events.get(events.size() - 1).setParticipants(new String[]{value.toString()});
                break;
            case "date":
                events.add(new Event());
                events.get(events.size() - 1).setDate(value.toString());
                break;
            case "content":
                events.get(events.size() - 1).setContent(value.toString());
                break;
            case "objectid":
                events.get(events.size() - 1).setInternalObjectId(value.toString());

            default:
                break;


        }

    }

    public void setNewEventObjectID(String id) {
        events.get(events.size() - 1).setInternalObjectId(id);
    }

    public void setEventInternalId(String internalObjectId) {
        events.get(events.size() - 1).setInternalObjectId(internalObjectId);
    }

    public Event getLastEvent() {
        return events.get(events.size() - 1);
    }
}
