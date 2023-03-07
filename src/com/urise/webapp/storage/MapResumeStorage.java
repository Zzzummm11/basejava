package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    protected final Map<String, Resume> map = new HashMap<>();

    @Override
    protected boolean isExist(final Resume searchKey) {
        return map.containsValue(searchKey);
    }

    @Override
    protected Resume getSearchKey(final String uuid) {
        return new Resume(uuid);
    }

    @Override
    protected void doSave(final Resume r, final Resume searchKey) {
        map.putIfAbsent(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(final Resume searchKey) {
        return map.get(searchKey.getUuid());
    }

    @Override
    protected void doUpdate(final Resume searchKey, final Resume r) {
        map.put(searchKey.getUuid(), r);
    }

    @Override
    protected void doDelete(final Resume searchKey) {
        map.remove(searchKey.getUuid());
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

