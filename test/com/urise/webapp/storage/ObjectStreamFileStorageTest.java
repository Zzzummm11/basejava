package com.urise.webapp.storage;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {
    private static final Storage storage = new FileStorage(STORAGE_DIR, new ObjectStream());

    public ObjectStreamFileStorageTest() {
        super(storage);
    }
}
