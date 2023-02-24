package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;


public class ListStorage extends AbstractStorage {

    protected final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected boolean isExist(final Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected Object getSearchKey(final String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected void doSave(final Resume r, final Object searchKey) {
        storage.add(r);
    }

    @Override
    protected Resume doGet(final Object searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    protected void doUpdate(final Object searchKey, Resume r) {
        storage.set((int) searchKey, r);
    }

    @Override
    protected void doDelete(final Object searchKey) {
        storage.remove((int) searchKey);
        storage.trimToSize();
    }

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

}
