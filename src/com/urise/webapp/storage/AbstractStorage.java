package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void addElement(final Resume r, final int index);
    protected abstract int getIndex(final String uuid);
    protected abstract int getIndex(final Resume r);
    protected abstract Resume getElement(final int index);
    protected abstract void updateElement(final int index, final Resume r);
    protected abstract void deleteElement(final int index);
    protected abstract boolean saveOverflow();


    public void save(final Resume r) {
        int index = getIndex(r);
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else if (saveOverflow()) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            addElement(r, index);
        }
    }

    public Resume get(final String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return getElement(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void update(final Resume r) {
        int index = getIndex(r);
        if (index >= 0) {
            updateElement(index, r);
        } else {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    public void delete(final String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteElement(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

}
