package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    protected final Map<String, Resume> map = new HashMap<>();

    @Override
    protected boolean isExist(final Object searchKey) {
        return map.containsKey((String) searchKey);
    }

    @Override
    protected Object getSearchKey(final String uuid) {
        return uuid;
    }

    @Override
    protected void doSave(final Resume r, final Object searchKey) {
        map.putIfAbsent((String) searchKey, r);
    }

    @Override
    protected Resume doGet(final Object searchKey) {
        return map.get((String) searchKey);
    }

    @Override
    protected void doUpdate(final Object searchKey, final Resume r) {
        map.put((String) searchKey, r);
    }

    @Override
    protected void doDelete(final Object searchKey) {
        map.remove((String) searchKey);
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

