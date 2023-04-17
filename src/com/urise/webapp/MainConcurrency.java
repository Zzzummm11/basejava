package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private static int counter;
    private static final Object LOCK = new Object();
    private static final int THREADS_NUMBER = 1000;

    public static void main(String[] args) {
        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        System.out.println(Thread.currentThread().getName());
        new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + " " + getState());
            }
        }.start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getState());
        }).start();

        Object lock = new Object();

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc2();
                }
            });
            thread.start();
            threads.add(thread);

        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // Thread.sleep(500);
        System.out.println(counter);
    }

    private static synchronized void inc() {
        counter++;
    }

    private void inc2() {
        // double a = Math.sin(13.);
        synchronized (this) {
            counter++;

        }

    }
}
