package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage extends AbstractArrayStorage {


    @Override
    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index != -1) {
            System.out.println("ERROR_SAVE: Resume with UUID " + "\"" + r + "\"" + " is already in Database");
        } else if (count == STORAGE_LIMIT) {
            System.out.println("ERROR_SAVE: Memory is full");
        } else {
            storage[count++] = r;
        }
    }

    @Override
    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index != -1) {
            storage[index] = r;
        } else {
            System.out.println("ERROR_UPDATE: Resume with UUID " + "\"" + r + "\"" + " doesn't exist in Database");
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            storage[index] = storage[count - 1];
            storage[count - 1] = null;
            count--;
        } else {
            System.out.println("ERROR_DELETE: Resume with UUID " + "\"" + uuid + "\"" + " doesn't exist in Database");
        }
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < count; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

}
