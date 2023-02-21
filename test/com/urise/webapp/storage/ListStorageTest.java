package com.urise.webapp.storage;

public class ListStorageTest extends AbstractStorageTest {
    private static final Storage storage = new ListStorage();


    public ListStorageTest() {
        super(storage);
    }
}