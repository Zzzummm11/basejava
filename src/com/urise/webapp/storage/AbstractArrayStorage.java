package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int count;

    protected abstract void addArrayElement(final Resume r, final int index);
    protected abstract void deleteArrayElement(final int index);


    @Override
    protected void addElement(final Resume r, final int index) {
        addArrayElement(r, index);
        count++;
    }
    @Override
    protected boolean saveOverflow(){
        return count == STORAGE_LIMIT;
    }
    @Override
    protected int getIndex(final Resume r) {
        return getIndex(r.getUuid());
    }
    @Override
    protected Resume getElement(final int index) {
        return storage[index];
    }
    @Override
    protected void updateElement(final int index, Resume r) {
        storage[index] = r;
    }
    @Override
    protected void deleteElement(final int index) {
        deleteArrayElement(index);
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
