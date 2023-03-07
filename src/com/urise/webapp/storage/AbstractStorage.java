package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.List;

public abstract class AbstractStorage <SK> implements Storage {

    protected abstract boolean isExist(final SK searchKey);

    protected abstract SK getSearchKey(final String uuid);

    protected abstract void doSave(final Resume r, final SK searchKey);

    protected abstract Resume doGet(final SK searchKey);

    protected abstract void doUpdate(final SK searchKey, final Resume r);

    protected abstract void doDelete(final SK searchKey);

    protected abstract List<Resume> convertToList();


    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            return searchKey;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    public void save(final Resume r) {
        SK searchKey = getNotExistingSearchKey(r.getUuid());
        doSave(r, searchKey);
    }

    public Resume get(final String uuid) {
        SK searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public void update(final Resume r) {
        SK searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(searchKey, r);

    }

    public void delete(final String uuid) {
        SK searchKey = getExistingSearchKey(uuid);
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
