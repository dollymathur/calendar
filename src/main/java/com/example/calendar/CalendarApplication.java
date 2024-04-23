package com.example.calendar;

import com.example.calendar.model.EventStatus;
import com.example.calendar.model.Slot;
import com.example.calendar.repository.bookedSlot.BookedSlotRepository;
import com.example.calendar.repository.event.EventRepository;
import com.example.calendar.service.BookedSlotService;
import com.example.calendar.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

public class CalendarApplication {

    public static void main(String[] args) {

        BookedSlotRepository bookedSlotRepository = new BookedSlotRepository();
        EventRepository eventRepository = new EventRepository();

        BookedSlotService bookedSlotService = new BookedSlotService(bookedSlotRepository);
        EventService eventService = new EventService(eventRepository, bookedSlotService);

        LocalDateTime startTime = LocalDateTime.of(2024, 4, 24, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 4, 24, 11, 0);
        int eventId = eventService.createEvent(List.of(1, 2), "Location1", "Title1", startTime, endTime);
        bookedSlotService.showBookedSlots();
        eventService.showEvents();
        System.out.println("-----------------");
        eventService.updateEventStatus(1, 1, EventStatus.ACCEPTED);
        bookedSlotService.showBookedSlots();
        eventService.showEvents();
        System.out.println("-----------------");

        eventService.updateEventStatus(1, 2, EventStatus.ACCEPTED);
        bookedSlotService.showBookedSlots();
        eventService.showEvents();
        System.out.println("-----------------");

        eventService.createEvent(List.of(1, 2), "Location1", "Title1", startTime, endTime);
        bookedSlotService.showBookedSlots();
        eventService.showEvents();
        System.out.println("-----------------");

        eventService.updateEvent(1, new Slot(LocalDateTime.of(2024, 4, 24, 11, 0), LocalDateTime.of(2024, 4, 24, 12, 0)));
        bookedSlotService.freeSlot(1, new Slot(LocalDateTime.of(2024, 4, 24, 10, 0), LocalDateTime.of(2024, 4, 24, 11, 0)));
        bookedSlotService.showBookedSlots();
        eventService.showEvents();
        System.out.println("-----------------");
        eventService.updateEventStatus(1, 1, EventStatus.ACCEPTED);
        eventService.updateEventStatus(1, 2, EventStatus.DECLINED);
        bookedSlotService.showBookedSlots();
        eventService.showEvents();
        System.out.println("-----------------");

        bookedSlotService.getFreeSlotsForUsers(List.of(1, 2), LocalDateTime.of(2024, 4, 24, 9, 0),
                LocalDateTime.of(2024, 4, 24, 13, 0));
    }
}
