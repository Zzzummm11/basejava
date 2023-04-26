package com.urise.webapp.storage;

import com.urise.webapp.model.storage.FileStorage;
import com.urise.webapp.model.storage.Storage;
import com.urise.webapp.model.storage.stream.ObjectStream;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {
    private static final Storage storage = new FileStorage(STORAGE_DIR, new ObjectStream());

    public ObjectStreamFileStorageTest() {
        super(storage);
    }
}
