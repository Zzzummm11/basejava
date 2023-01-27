package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    private final Resume[] storage = new Resume[100];
    private int count;


    public void clear() {
        /* Arrays.fill(storage, 0, count, null);  - можно ли весто цикла for использовать Arrays.fill? */

        for (int i = 0; i < count; i++) {
            storage[i] = null;
        }
        count = 0;
    }

    public void save(Resume r) {
        int index = findUuid(r.toString());
        if (index != -1) {
            System.out.println("ERROR_SAVE: Resume with UUID " + "\"" + r + "\"" + " is already in Database");
        } else {
            storage[count++] = r;
        }
    }

    public void update(Resume r) {
        int index = findUuid(r.toString());
        if (index != -1) {
            storage[index] = r;
        } else {
            System.out.println("ERROR_UPDATE: Resume with UUID " + "\"" + r + "\"" + " doesn't exist in Database");
        }
    }

    public Resume get(String uuid) {
        if (findUuid(uuid) != -1) {
            return storage[findUuid(uuid)];
        } else {
            System.out.println("ERROR_GET: Resume with UUID " + "\"" + uuid + "\"" + " doesn't exist in Database");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = findUuid(uuid);
        if (index != -1) {
            System.arraycopy(storage, index + 1, storage, index, count - 1 - index);
            count--;
        } else {
            System.out.println("ERROR_DELETE: Resume with UUID " + "\"" + uuid + "\"" + " doesn't exist in Database");
        }
    }

    private int findUuid(String uuid) {
        for (int i = 0; i < count; i++) {
            if (uuid.equals(storage[i].toString())) {
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
