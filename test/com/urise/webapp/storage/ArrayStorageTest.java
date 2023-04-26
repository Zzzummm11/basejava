package com.urise.webapp.storage;

import com.urise.webapp.model.storage.ArrayStorage;
import com.urise.webapp.model.storage.Storage;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    private static final Storage storage = new ArrayStorage();
    public ArrayStorageTest() {
        super(storage);
    }
}