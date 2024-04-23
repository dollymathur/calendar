package com.example.calendar.repository.event;

import com.example.calendar.model.Event;
import com.example.calendar.model.EventStatus;

import java.util.HashMap;
import java.util.Map;

public class EventRepository {

    EventInventory eventInventory = new EventInventory();
    int id = 1;

    public int addEvent(Event event) {
        event.setId(id++);
        event.setEventStatus(initiateEvent(event.getEventStatus()));
        eventInventory.events.add(event);
        return id;
    }

    public void showEvents() {
        for (Event event : eventInventory.events) {
            System.out.println(event);
        }
    }

    public Event getEventById(int eventId) {
        for (Event event : eventInventory.events) {
            if (event.getId() == eventId) {
                return event;
            }
        }
        return null;
    }

    public void deleteEvent(int eventId) {
        for (Event event : eventInventory.events) {
            if (event.getId() == eventId) {
                eventInventory.events.remove(event);
                return;
            }
        }
    }

    public void updateEvent(int eventId, Event event) {
        for (Event e : eventInventory.events) {
            if (e.getId() == eventId) {
                e = event;
            }
        }
    }

    private HashMap<Integer, EventStatus> initiateEvent(HashMap<Integer, EventStatus> eventStatus) {
        for (Map.Entry<Integer, EventStatus> entry : eventStatus.entrySet()) {
            entry.setValue(EventStatus.PENDING);
        }
        return eventStatus;
    }
}
