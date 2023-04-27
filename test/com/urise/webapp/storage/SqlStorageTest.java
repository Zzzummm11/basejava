package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.model.storage.Storage;

public class SqlStorageTest extends AbstractStorageTest {
    private static final Storage storage = Config.get().getStorage();
    public SqlStorageTest() {
        super(storage);
    }
}
