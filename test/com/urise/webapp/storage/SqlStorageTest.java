package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.model.storage.SqlStorage;
import com.urise.webapp.model.storage.Storage;

public class SqlStorageTest extends AbstractStorageTest {
    private static final String URL = Config.get().getUrl();
    private static final String USER = Config.get().getUser();
    private static final String PASSWORD = Config.get().getPassword();
    private static final Storage storage = new SqlStorage(URL, USER, PASSWORD);

    public SqlStorageTest() {
        super(storage);
    }
}
