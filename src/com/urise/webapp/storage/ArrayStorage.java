package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int count;


    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    public void save(Resume r) {
        int index = findUuid(r.getUuid());
        if (index != -1) {
            System.out.println("ERROR_SAVE: Resume with UUID " + "\"" + r + "\"" + " is already in Database");
        } else if (count == STORAGE_LIMIT) {
            System.out.println("ERROR_SAVE: Memory is full");
        } else {
            storage[count++] = r;
        }
    }

    public void update(Resume r) {
        int index = findUuid(r.getUuid());
        if (index != -1) {
            storage[index] = r;
        } else {
            System.out.println("ERROR_UPDATE: Resume with UUID " + "\"" + r + "\"" + " doesn't exist in Database");
        }
    }

    public Resume get(String uuid) {
        int index = findUuid(uuid);
        if (index != -1) {
            return storage[index];
        } else {
            System.out.println("ERROR_GET: Resume with UUID " + "\"" + uuid + "\"" + " doesn't exist in Database");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = findUuid(uuid);
        if (index != -1) {
            storage[index] = storage[count - 1];
            storage[count - 1] = null;
            count--;
        } else {
            System.out.println("ERROR_DELETE: Resume with UUID " + "\"" + uuid + "\"" + " doesn't exist in Database");
        }
    }

    private int findUuid(String uuid) {
        for (int i = 0; i < count; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    public int size() {
        return count;
    }
}
