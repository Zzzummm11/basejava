package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ArrayStorage;

public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid_R1");
        Resume r2 = new Resume("uuid_R2");
        Resume r3 = new Resume("uuid_R1");
        Resume r4 = new Resume("uuid_R1");
        Resume r5 = new Resume("uuid_R5");

        ARRAY_STORAGE.save(r1);
        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.toString()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        ARRAY_STORAGE.save(r2);
        System.out.println("Get r2: " + ARRAY_STORAGE.get(r2.toString()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        ARRAY_STORAGE.save(r3);
        System.out.println("Get r3: " + ARRAY_STORAGE.get(r3.toString()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r2.toString());

        printAll();
        ARRAY_STORAGE.delete("uuid_R0");
        printAll();

        ARRAY_STORAGE.update(r4);
        System.out.println("Get r4: " + ARRAY_STORAGE.get(r4.toString()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        ARRAY_STORAGE.update(r5);
        System.out.println("Get r5: " + ARRAY_STORAGE.get(r5.toString()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        ARRAY_STORAGE.clear();
        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size());


    }

    static void printAll() {
        System.out.println("\nGet All:");
        System.out.println("-----------------");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
