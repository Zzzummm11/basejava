package com.urise.webapp.storage;

import com.urise.webapp.model.storage.MapUuidStorage;
import com.urise.webapp.model.storage.Storage;

public class MapUuidStorageTest extends AbstractStorageTest{
    private static final Storage map = new MapUuidStorage();


    public MapUuidStorageTest() {
        super(map);
    }
}
