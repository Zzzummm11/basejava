package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.storage.Storage;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static com.urise.webapp.ResumeTestData.createResume;
import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String FULLNAME_1 = "fullName_1";
    private static final String UUID_2 = "uuid2";
    private static final String FULLNAME_2 = "fullName_2";
    private static final String UUID_3 = "uuid3";
    private static final String FULLNAME_3 = "fullName_3";
    private static final String UUID_4 = "uuid4";
    private static final String FULLNAME_4 = "fullName_4";
    private static final String UUID_31 = "uuid3";
    private static final String FULLNAME_31 = "fullName_31";
    private static final String UUID_NOT_EXIST = "dummy";
    private static final Resume RESUME_1 = createResume(UUID_1, FULLNAME_1);
    private static final Resume RESUME_2 = createResume(UUID_2, FULLNAME_2);
    private static final Resume RESUME_3 = createResume(UUID_3, FULLNAME_3);
    private static final Resume RESUME_4 = createResume(UUID_4, FULLNAME_4);
    private static final Resume RESUME_31 = new Resume(UUID_31, FULLNAME_31);


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
        assertTrue(RESUME_31.equals(storage.get(RESUME_31.getUuid())));
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

    @Test(expected = Exception.class)
    public void saveExist() {
        storage.save(RESUME_31);
    }

    @Test()
    public void delete() throws NotExistStorageException {
        storage.delete(UUID_3);
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
        storage.delete(UUID_4);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        assertArrayEquals(new Resume[]{}, storage.getAllSorted().toArray());
    }

    @Test
    public void getAllSorted() {
        assertArrayEquals(new Resume[]{RESUME_1, RESUME_2, RESUME_3}, storage.getAllSorted().toArray());
    }

}