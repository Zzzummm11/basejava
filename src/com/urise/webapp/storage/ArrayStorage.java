package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(final String uuid) {
        for (int i = 0; i < count; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void addElement(final Resume r, final int index) {
        storage[count++] = r;
    }

    @Override
    protected void deleteElement(final int index) {
        storage[index] = storage[count - 1];
        storage[count - 1] = null;
        count--;
    }



}
