package com.urise.webapp;

import com.urise.webapp.model.Resume;

public class DeadlockExample {

    public static void main(String[] args) {

        Resume r1 = new Resume();
        Resume r2 = new Resume();


        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": start");
            synchronized (r1) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(Thread.currentThread().getName() + " trying to work with r2");

                synchronized (r2) {
                }

                System.out.println(Thread.currentThread().getName() + " finish");
            }
        }, "Thread_1");

        Thread thread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": start");
            synchronized (r2) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(Thread.currentThread().getName() + " trying to work with r1");

                synchronized (r1) {
                }

                System.out.println(Thread.currentThread().getName() + " finish");
            }
        }, "Thread_2");

        thread1.start();
        thread2.start();

    }
}

