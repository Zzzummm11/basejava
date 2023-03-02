package com.urise.webapp.storage;

public class MapResumeStorageTest extends AbstractStorageTest{
    private static final Storage map = new MapResumeStorage();


    public MapResumeStorageTest() {
        super(map);
    }
}
