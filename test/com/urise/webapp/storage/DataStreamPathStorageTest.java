package com.urise.webapp.storage;

import com.urise.webapp.model.storage.PathStorage;
import com.urise.webapp.model.storage.Storage;
import com.urise.webapp.model.storage.stream.DataStream;

public class DataStreamPathStorageTest extends AbstractStorageTest{
    private static final Storage storage = new PathStorage(String.valueOf(STORAGE_DIR),new DataStream());

    public DataStreamPathStorageTest() {
        super(storage);
    }
}
