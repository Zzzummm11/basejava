package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
    protected final Map<String, Resume> map = new HashMap<>();

    @Override
    protected boolean isExist(final String searchKey) {
        return map.containsKey(searchKey);
    }

    @Override
    protected String getSearchKey(final String uuid) {
        return uuid;
    }

    @Override
    protected void doSave(final Resume r, final String searchKey) {
        map.putIfAbsent(searchKey, r);
    }

    @Override
    protected Resume doGet(final String searchKey) {
        return map.get(searchKey);
    }

    @Override
    protected void doUpdate(final String searchKey, final Resume r) {
        map.put(searchKey, r);
    }

    @Override
    protected void doDelete(final String searchKey) {
        map.remove(searchKey);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> convertToList() {
        return new ArrayList<>(map.values());
    }

}

