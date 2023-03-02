package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    protected final Map<String, Resume> map = new HashMap<>();

    @Override
    protected boolean isExist(final Object searchKey) {
        return map.containsValue((Resume) searchKey);
    }

    @Override
    protected Object getSearchKey(final String uuid) {
        return new Resume(uuid);
    }

    @Override
    protected void doSave(final Resume r, final Object searchKey) {
        map.putIfAbsent(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(final Object searchKey) {
        Resume searchKeyResume = (Resume) searchKey;
        return map.get(searchKeyResume.getUuid());
    }

    @Override
    protected void doUpdate(final Object searchKey, final Resume r) {
        Resume searchKeyUpdate = (Resume) searchKey;
        map.put(searchKeyUpdate.getUuid(), r);
    }

    @Override
    protected void doDelete(final Object searchKey) {
        Resume searchKeyRemove = (Resume) searchKey;
        map.remove(searchKeyRemove.getUuid());
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

