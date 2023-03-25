package com.urise.webapp.storage;

public class ObjectStreamStorageTest extends AbstractStorageTest{
    private static final Storage storage = new ObjectStreamStorage(STORAGE_DIR);

    public ObjectStreamStorageTest() {
        super(storage);
    }
}
