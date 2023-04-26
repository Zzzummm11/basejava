package com.urise.webapp.storage;

import com.urise.webapp.model.storage.SortedArrayStorage;
import com.urise.webapp.model.storage.Storage;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    private static final Storage storage = new SortedArrayStorage();
    public SortedArrayStorageTest() {
        super(storage);
    }
}