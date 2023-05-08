package com.urise.webapp.storage;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    public static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int count;

    protected abstract void addElement(final Resume r, final int index);

    protected abstract void deleteElement(final int index);

    @Override
    protected boolean isExist(final Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void doSave(final Resume r, final Integer searchKey) {
        if (count == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        addElement(r, searchKey);
        count++;
    }

    @Override
    protected Resume doGet(final Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected void doUpdate(final Integer searchKey, Resume r) {
        storage[searchKey] = r;
    }

    @Override
    protected void doDelete(final Integer searchKey) {
        deleteElement(searchKey);
        count--;
    }

    public int size() {
        return count;
    }

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    @Override
    public List<Resume> convertToList() {
        return new ArrayList<>(Arrays.asList(Arrays.copyOf(storage, count)));
    }

}
