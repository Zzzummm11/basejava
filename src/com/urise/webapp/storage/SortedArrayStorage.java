package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void addElement(final Resume r, final int index) {
        int insertionIndex = -index - 1;
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, count - insertionIndex);
        storage[insertionIndex] = r;
    }

    @Override
    protected Integer getSearchKey(final String uuid) {

        return Arrays.binarySearch(storage, 0, count, new Resume(uuid, "fullName"));
    }

    @Override
    protected void deleteElement(final int index) {
        System.arraycopy(storage, index + 1, storage, index, count - 1 - index);
    }
}
