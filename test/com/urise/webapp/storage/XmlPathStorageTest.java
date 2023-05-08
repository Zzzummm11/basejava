package com.urise.webapp.storage;

import com.urise.webapp.storage.stream.XmlStream;

public class XmlPathStorageTest extends AbstractStorageTest{
    private static final Storage storage = new PathStorage(String.valueOf(STORAGE_DIR),new XmlStream());

    public XmlPathStorageTest() {
        super(storage);
    }
}
