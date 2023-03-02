package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected abstract boolean isExist(final Object searchKey);

    protected abstract Object getSearchKey(final String uuid);

    protected abstract void doSave(final Resume r, final Object searchKey);

    protected abstract Resume doGet(final Object searchKey);

    protected abstract void doUpdate(final Object searchKey, final Resume r);

    protected abstract void doDelete(final Object searchKey);

    protected abstract List<Resume> convertToList();


    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            return searchKey;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    public void save(final Resume r) {
        Object searchKey = getNotExistingSearchKey(r.getUuid());
        doSave(r, searchKey);
    }

    public Resume get(final String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public void update(final Resume r) {
        Object searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(searchKey, r);

    }

    public void delete(final String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    public List<Resume> getAllSorted() {
        List<Resume> list = convertToList();
        list.sort((o1, o2) -> {
            if (o1.getFullName().equals(o2.getFullName())) {
                return o1.getUuid().compareTo(o2.getUuid());
            } else {
                return o1.getFullName().compareTo(o2.getFullName());
            }
        });
        return list;
    }

}
