package com.urise.webapp.storage;

import com.urise.webapp.storage.stream.ObjectStream;

public class ObjectStreamPathStorageTest extends AbstractStorageTest{
    private static final Storage storage = new PathStorage(String.valueOf(STORAGE_DIR),new ObjectStream());

    public ObjectStreamPathStorageTest() {
        super(storage);
    }
}
