package com.urise.webapp.storage;

public class MapUuidStorageTest extends AbstractStorageTest{
    private static final Storage map = new MapUuidStorage();


    public MapUuidStorageTest() {
        super(map);
    }
}
