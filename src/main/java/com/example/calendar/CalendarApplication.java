package com.example.calendar;

public class CalendarApplication {

    public static void main(String[] args) {
        System.out.println("Thread " + Thread.currentThread().getName() + " is running");
        Thread t1 = new Thread(new RunnableImpl());
        t1.start();

        Thread t2 = new Thread(new RunnableImpl());
        t2.start();
    }
}
