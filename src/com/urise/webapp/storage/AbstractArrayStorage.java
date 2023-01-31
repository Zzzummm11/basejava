package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int count;


    public int size() {
        return count;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            return storage[index];
        } else {
            System.out.println("ERROR_GET: Resume with UUID " + "\"" + uuid + "\"" + " doesn't exist in Database");
            return null;
        }
    }

    protected abstract int getIndex(final String uuid);

}
