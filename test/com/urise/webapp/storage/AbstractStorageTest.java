package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.storage.Storage;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static com.urise.webapp.model.storage.AbstractStorage.COMPARE_RESUME;
import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected final Storage storage;
    private static final String FULL_NAME_1 = "fullName_2";
    private static final String FULL_NAME_2 = "fullName_1";
    private static final String FULL_NAME_3 = "fullName_3";
    private static final String FULL_NAME_4 = "fullName_4";
    private static final String FULL_NAME_31 = "fullName_31";
    private static final String UUID_NOT_EXIST = "dummy";
    private static final Resume RESUME_1 = new Resume(FULL_NAME_1);
    private static final Resume RESUME_2 = new Resume(FULL_NAME_2);
    private static final Resume RESUME_3 = new Resume(FULL_NAME_3);
    private static final Resume RESUME_4 = new Resume(FULL_NAME_4);
    private static final Resume RESUME_31 = new Resume(RESUME_3.getUuid(), FULL_NAME_31);


    public AbstractStorageTest(final Storage storage) {
        this.storage = storage;
    }


    @Before
    public void setUp() {
        storage.clear();

        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    public void assertSize(int numb) {
        assertEquals(numb, storage.size());
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    public void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXIST);
    }

    @Test
    public void update() {
        storage.update(RESUME_31);
        assertEquals(RESUME_31, storage.get(RESUME_31.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_31);
    }

    @Test()
    public void delete() throws NotExistStorageException {
        storage.delete(RESUME_3.getUuid());
        assertSize(2);
        try {
            assertGet(RESUME_3);
            fail("Expected NotExistStorageException");
        } catch (NotExistStorageException thrown) {
            assertNotEquals("", thrown.getMessage());
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(RESUME_4.getUuid());
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        assertArrayEquals(new Resume[]{}, storage.getAllSorted().toArray());
    }

    @Test
    public void getAllSorted() {
        assertArrayEquals(Arrays.stream(new Resume[]{RESUME_2, RESUME_1, RESUME_3}).sorted(COMPARE_RESUME).toArray(), storage.getAllSorted().toArray());
    }

}