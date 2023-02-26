package com.urise.webapp.storage;

public class MapStorageTest extends AbstractStorageTest{
    private static final Storage map = new MapStorage();


    public MapStorageTest() {
        super(map);
    }
}
