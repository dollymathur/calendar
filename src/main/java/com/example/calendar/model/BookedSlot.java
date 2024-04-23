package com.example.calendar.model;

import java.util.List;

public class BookedSlot {
    int userId;
    List<Slot> slots;

    public BookedSlot(int userId, List<Slot> slots) {
        this.userId = userId;
        this.slots = slots;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    @Override
    public String toString() {
        return "BookedSlot{" +
                "userId=" + userId +
                ", slots=" + slots +
                '}';
    }
}
