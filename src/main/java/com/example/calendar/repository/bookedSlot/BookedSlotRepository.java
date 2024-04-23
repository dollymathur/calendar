package com.example.calendar.repository.bookedSlot;

import com.example.calendar.model.BookedSlot;
import com.example.calendar.model.Slot;

import java.util.ArrayList;
import java.util.List;

public class BookedSlotRepository {

    BookedSlotInventory bookedSlotInventory = new BookedSlotInventory();

    public void showBookedSlots() {
        for (BookedSlot bookedSlot : bookedSlotInventory.bookedSlots) {
            System.out.println(bookedSlot);
        }
    }

    public void addBookedSlotByUserId(int userId, Slot slot) {
        for (BookedSlot bookedSlot : bookedSlotInventory.bookedSlots) {
            if (bookedSlot.getUserId() == userId) {
                List<Slot> localSlots = new ArrayList<>(bookedSlot.getSlots());
                localSlots.add(slot);
                bookedSlot.setSlots(localSlots);
                return;
            }
        }
        List<Slot> slots = new ArrayList<>();
        slots.add(slot);
        bookedSlotInventory.bookedSlots.add(new BookedSlot(userId, slots));

    }

    public void freeSlotByUserId(int userId, Slot slot) {
        List<BookedSlot> bookedSlots = bookedSlotInventory.bookedSlots;
        for (BookedSlot bookedSlot : bookedSlots) {
            if (bookedSlot.getUserId() == userId) {
                List<Slot> slots = bookedSlot.getSlots();
                for (Slot s : slots) {
                    if (s.getStartTime().equals(slot.getStartTime()) && s.getEndTime().equals(slot.getEndTime())) {
                        slots.remove(s);
                        return;
                    }
                }
            }
        }
    }

    public List<Slot> getAllBookedSlotsByUserId(int userId) {
        for (BookedSlot bookedSlot : bookedSlotInventory.bookedSlots) {
            if (bookedSlot.getUserId() == userId) {
                return bookedSlot.getSlots();
            }
        }
        return null;
    }

}
