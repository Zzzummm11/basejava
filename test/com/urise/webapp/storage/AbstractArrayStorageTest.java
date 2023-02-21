package com.urise.webapp.storage;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static java.lang.String.valueOf;
import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected final Storage storage;

    public AbstractArrayStorageTest(final Storage storage) {
        super(storage);
        this.storage = storage;
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume(valueOf(i)));
            }
        } catch (Exception e) {
            fail("Storage already overflow");
        }
        storage.save(new Resume(valueOf(STORAGE_LIMIT)));
    }
}