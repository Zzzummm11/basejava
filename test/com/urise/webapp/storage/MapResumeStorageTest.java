package com.urise.webapp.storage;

import com.urise.webapp.model.storage.MapResumeStorage;
import com.urise.webapp.model.storage.Storage;

public class MapResumeStorageTest extends AbstractStorageTest{
    private static final Storage map = new MapResumeStorage();


    public MapResumeStorageTest() {
        super(map);
    }
}
