package com.urise.webapp.storage;

import com.urise.webapp.model.storage.PathStorage;
import com.urise.webapp.model.storage.Storage;
import com.urise.webapp.model.storage.stream.ObjectStream;

public class ObjectStreamPathStorageTest extends AbstractStorageTest{
    private static final Storage storage = new PathStorage(String.valueOf(STORAGE_DIR),new ObjectStream());

    public ObjectStreamPathStorageTest() {
        super(storage);
    }
}
