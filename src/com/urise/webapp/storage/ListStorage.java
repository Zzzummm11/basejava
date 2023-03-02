package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;


public class ListStorage extends AbstractStorage {

    private final List<Resume> list = new ArrayList<>();

    @Override
    protected boolean isExist(final Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected Integer getSearchKey(final String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected void doSave(final Resume r, final Object searchKey) {
        list.add(r);
    }

    @Override
    protected Resume doGet(final Object searchKey) {
        return list.get((int) searchKey);
    }

    @Override
    protected void doUpdate(final Object searchKey, Resume r) {
        list.set((int) searchKey, r);
    }

    @Override
    protected void doDelete(final Object searchKey) {
        list.remove((int) searchKey);
    }

    public int size() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    @Override
    public List<Resume> convertToList() {
        return list;
    }

}
