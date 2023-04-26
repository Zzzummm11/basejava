package com.urise.webapp.model.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;


public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> list = new ArrayList<>();

    @Override
    protected boolean isExist(final Integer searchKey) {
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
    protected void doSave(final Resume r, final Integer searchKey) {
        list.add(r);
    }

    @Override
    protected Resume doGet(final Integer searchKey) {
        return list.get(searchKey);
    }

    @Override
    protected void doUpdate(final Integer searchKey, Resume r) {
        list.set(searchKey, r);
    }

    @Override
    protected void doDelete(final Integer searchKey) {
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
