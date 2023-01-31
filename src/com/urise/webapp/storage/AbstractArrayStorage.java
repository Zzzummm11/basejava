package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int count;


    protected abstract int getIndex(final String uuid);

    protected abstract void addElement(final Resume r, final int index);

    protected abstract void deleteElement(final int index);


    public int size() {
        return count;
    }

    public Resume get(final String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("ERROR_GET: Resume with UUID " + "\"" + uuid + "\"" + " doesn't exist in Database");
            return null;
        }
    }

    public void update(final Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else {
            System.out.println("ERROR_UPDATE: Resume with UUID " + "\"" + r + "\"" + " doesn't exist in Database");
        }
    }

    public void save(final Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            System.out.println("ERROR_SAVE: Resume with UUID " + "\"" + r + "\"" + " is already in Database");
        } else if (count == STORAGE_LIMIT) {
            System.out.println("ERROR_SAVE: Memory is full");
        } else {
            addElement(r,index);
        }
    }

    public void delete(final String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteElement(index);
        } else {
            System.out.println("ERROR_DELETE: Resume with UUID " + "\"" + uuid + "\"" + " doesn't exist in Database");
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

}
