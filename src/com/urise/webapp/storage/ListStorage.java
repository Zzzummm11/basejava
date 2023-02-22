package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;


public class ListStorage extends AbstractStorage {

    protected final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected void addElement(final Resume r, final int index) {
        storage.add(r);
    }
    @Override
    protected boolean saveOverflow(){
        return false;
    }
    @Override
    protected int getIndex(final String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected int getIndex(final Resume r) {
        return storage.indexOf(r);
    }

    @Override
    protected Resume getElement(final int index) {
        return storage.get(index);
    }

    @Override
    protected void updateElement(final int index, final Resume r) {
        storage.set(index, r);
    }

    @Override
    protected void deleteElement(final int index) {
        storage.remove(index);
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
