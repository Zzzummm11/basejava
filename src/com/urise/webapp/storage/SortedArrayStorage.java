package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(final String uuid) {
        return Arrays.binarySearch(storage, 0, count, new Resume(uuid));
    }

    @Override
    protected void addElement(final Resume r, final int index) {
        if (index == -1) {
            if (count == 0) {
                storage[0] = r;
            } else {
                Resume[] tempStorage = new Resume[count];
                System.arraycopy(storage, 0, tempStorage, 0, count);
                storage[0] = r;
                System.arraycopy(tempStorage, 0, storage, 1, count);
            }
        } else {
            storage[count] = r;
        }
    }

    @Override
    protected void deleteElement(final int index) {
        System.arraycopy(storage, index + 1, storage, index, count - 1 - index);
    }
}
