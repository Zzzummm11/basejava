package com.urise.webapp.storage;

import com.urise.webapp.model.storage.ListStorage;
import com.urise.webapp.model.storage.Storage;

public class ListStorageTest extends AbstractStorageTest {
    private static final Storage storage = new ListStorage();


    public ListStorageTest() {
        super(storage);
    }
}