package com.example.calendar.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event {
    int id;
    Slot slot;
    String location;
    HashMap<Integer, EventStatus> eventStatus;
    String title;

    public Event(Slot slot, String location, HashMap<Integer, EventStatus> eventStatus, String title) {
        this.slot = slot;
        this.location = location;
        this.eventStatus = eventStatus;
        this.title = title;
    }

    public Event(Slot slot, String location, List<Integer> userIds, String title) {
        this.slot = slot;
        this.location = location;
        this.eventStatus = initiateEventStatus(userIds);
        this.title = title;
    }

    public HashMap<Integer, EventStatus> initiateEventStatus(List<Integer> userIds) {
        HashMap<Integer, EventStatus> eventStatus = new HashMap<>();
        for (Integer userId : userIds) {
            eventStatus.put(userId, EventStatus.PENDING);
        }
        return eventStatus;
    }

    public List<Integer> getInvitedUsers() {

        List<Integer> userIds = new ArrayList<>();

        for (Map.Entry<Integer, EventStatus> entry : eventStatus.entrySet()) {
            if (entry.getValue() == EventStatus.ACCEPTED) {
                userIds.add(entry.getKey());
            }
        }
        return userIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public HashMap<Integer, EventStatus> getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(HashMap<Integer, EventStatus> eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", slot=" + slot +
                ", location='" + location + '\'' +
                ", eventStatus=" + eventStatus +
                ", title='" + title + '\'' +
                '}';
    }
}
