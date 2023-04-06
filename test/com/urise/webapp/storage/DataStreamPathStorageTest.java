package com.urise.webapp.storage;

import com.urise.webapp.storage.stream.DataStream;

public class DataStreamPathStorageTest extends AbstractStorageTest{
    private static final Storage storage = new PathStorage(String.valueOf(STORAGE_DIR),new DataStream());

    public DataStreamPathStorageTest() {
        super(storage);
    }
}
