package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void clear() {

    }

    @Override
    public void save(final Resume r) {

    }

    @Override
    public void update(final Resume r) {

    }

    @Override
    public void delete(final String uuid) {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    protected int getIndex(final String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, count, searchKey);
    }
}
