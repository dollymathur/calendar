package com.example.calendar.service;

import com.example.calendar.model.BookedSlot;
import com.example.calendar.model.Event;
import com.example.calendar.model.EventStatus;
import com.example.calendar.model.Slot;
import com.example.calendar.repository.event.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class EventService {

    EventRepository eventRepository;
    BookedSlotService bookedSlotService;

    public EventService(EventRepository eventRepository, BookedSlotService bookedSlotService) {
        this.eventRepository = eventRepository;
        this.bookedSlotService = bookedSlotService;
    }

    public void hello() {
        System.out.println("Hello from EventService " + Thread.currentThread().getName());
    }

    public int createEvent(List<Integer> userIds, String location, String title, LocalDateTime startTime, LocalDateTime endTime) {
        boolean isSlotAvailable = bookedSlotService.isSlotAvailableForUsers(userIds, startTime, endTime);
        Slot slot = new Slot(startTime, endTime);

        if (isSlotAvailable) {
            Event event = new Event(slot, location, userIds, title);
            int eventId = eventRepository.addEvent(event);
            System.out.println("Event " + eventId + " created successfully");
            return eventId;
        }
        System.out.println("Slot not available");
        return -1;
    }

    public void updateEventStatus(int eventId, int userId, EventStatus status) {
        Event event = eventRepository.getEventById(eventId);
        if (event == null) {
            System.out.println("Event not found");
            return;
        }
        event.getEventStatus().put(userId, status);
        eventRepository.updateEvent(eventId, event);
        if (status == EventStatus.ACCEPTED) {
            bookedSlotService.addBookedSlot(userId, event.getSlot());
        }
        System.out.println("Event " + eventId + " updated successfully by user " + userId + " to " + status);
    }

    public void deleteEvent(int eventId) {
        Event event = eventRepository.getEventById(eventId);
        for(Map.Entry<Integer, EventStatus> entry : event.getEventStatus().entrySet()) {
            if (entry.getValue() == EventStatus.ACCEPTED) {
                bookedSlotService.freeSlot(entry.getKey(), event.getSlot());
                return;
            }
        }
        eventRepository.deleteEvent(eventId);
    }

    public void updateEvent(int eventId, Slot slot) {
        Event event = eventRepository.getEventById(eventId);
        boolean isSlotAvailable = bookedSlotService.isSlotAvailableForUsers(event.getInvitedUsers(), slot.getStartTime(), slot.getEndTime());
        if (isSlotAvailable) {
            Slot olderSlot = event.getSlot();
            event.setSlot(slot);
            for (Map.Entry<Integer, EventStatus> entry : event.getEventStatus().entrySet()) {
                if (entry.getValue() == EventStatus.ACCEPTED) {
                    bookedSlotService.freeSlot(entry.getKey(), olderSlot);
                }
            }
            event.setEventStatus(event.initiateEventStatus(event.getInvitedUsers()));
            eventRepository.updateEvent(eventId, event);
            System.out.println("Event " + eventId + " updated successfully");
            return;
        }
        System.out.println("Slot not available");
    }

    public void showEvents() {
        eventRepository.showEvents();
    }

}
