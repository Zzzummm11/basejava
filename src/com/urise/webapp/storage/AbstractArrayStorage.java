package com.urise.webapp.storage;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int count;

    protected abstract void addElement(final Resume r, final int index);

    protected abstract void deleteElement(final int index);

    @Override
    protected boolean isExist(final Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected void doSave(final Resume r, final Object searchKey) {
        if (count == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        addElement(r, (int) searchKey);
        count++;
    }

    @Override
    protected Resume doGet(final Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected void doUpdate(final Object searchKey, Resume r) {
        storage[(int) searchKey] = r;
    }

    @Override
    protected void doDelete(final Object searchKey) {
        deleteElement((int) searchKey);
        count--;
    }

    public int size() {
        return count;
    }

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

}
