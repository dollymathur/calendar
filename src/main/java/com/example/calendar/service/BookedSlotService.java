package com.example.calendar.service;

import com.example.calendar.model.BookedSlot;
import com.example.calendar.model.Slot;
import com.example.calendar.repository.bookedSlot.BookedSlotRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookedSlotService {

    BookedSlotRepository bookedSlotRepository;

    public BookedSlotService(BookedSlotRepository bookedSlotRepository) {
        this.bookedSlotRepository = bookedSlotRepository;
    }

    public void getBookedSlotsByUserId(int userId) {
        bookedSlotRepository.getAllBookedSlotsByUserId(userId);
    }

    public void addBookedSlot(int userId, Slot slot) {
        bookedSlotRepository.addBookedSlotByUserId(userId, slot);
    }

    public void freeSlot(int userId, Slot slot) {
        bookedSlotRepository.freeSlotByUserId(userId, slot);
    }

    public boolean isSlotAvailableForUsers(List<Integer> userIds, LocalDateTime startTime, LocalDateTime endTime) {
        for (Integer userId : userIds) {
            List<Slot> slots = bookedSlotRepository.getAllBookedSlotsByUserId(userId);
            if (slots == null) {
                return true;
            }
            for (Slot slot : slots) {
                if (slot.getStartTime() == startTime && slot.getEndTime() == endTime) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Slot> getFreeSlotsForUsers(List<Integer> userIds, LocalDateTime startTime, LocalDateTime endTime) {
        HashMap<Slot, Boolean> availabilityMap = new HashMap<>();
        for (LocalDateTime time = startTime; time.isBefore(endTime); time = time.plusHours(1)) {
            availabilityMap.put(new Slot(time, time.plusHours(1)), true);
        }

        List<Slot> freeSlots = new ArrayList<>();
        for (Integer userId : userIds) {
            List<Slot> slots = bookedSlotRepository.getAllBookedSlotsByUserId(userId);
            for (Slot slot : slots) {
                if ((slot.getStartTime().isAfter(startTime) || slot.getStartTime().equals(startTime)) &&
                        (slot.getEndTime().isBefore(endTime) || slot.getEndTime().equals(endTime))) {
                    for (Map.Entry<Slot, Boolean> entry : availabilityMap.entrySet()) {
                        if (entry.getKey().getStartTime().equals(slot.getStartTime()) && entry.getKey().getEndTime().equals(slot.getEndTime())) {
                            entry.setValue(false);
                        }
                    }
                }
            }
        }

        for (Slot slot : availabilityMap.keySet()) {
            if (availabilityMap.get(slot)) {
                freeSlots.add(slot);
            }
        }

        for (Slot slot : freeSlots) {
            System.out.println(slot);
        }

        return freeSlots;
    }

    public void showBookedSlots() {
        bookedSlotRepository.showBookedSlots();
    }
}
