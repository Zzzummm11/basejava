package com.urise.webapp;

import com.urise.webapp.model.Resume;

public class DeadlockExample {

    public static void main(String[] args) {

        Resume r1 = new Resume();
        Resume r2 = new Resume();

        createAndStartThread(r1,r2);
        createAndStartThread(r2,r1);

    }
    public static void createAndStartThread(Resume r1, Resume r2){
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": start");
            synchronized (r1) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(Thread.currentThread().getName() + " trying to work with first resume");

                synchronized (r2) {
                }

                System.out.println(Thread.currentThread().getName() + " finish");
            }
        }).start();
    }
}

