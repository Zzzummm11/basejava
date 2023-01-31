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
        if (count == 0) {
            storage[0] = r;
        } else if (index == -1) {

        } else {

        }

        count++;
    }

    @Override
    protected void deleteElement(final int index) {
        System.arraycopy(storage, index + 1, storage, index, count - 1 - index);
        count--;
    }
}
