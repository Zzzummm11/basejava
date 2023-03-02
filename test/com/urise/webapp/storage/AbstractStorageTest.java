package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {

    protected final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String FULLNAME_1 = "fullName_2";
    private static final String UUID_2 = "uuid2";
    private static final String FULLNAME_2 = "fullName_1";
    private static final String UUID_3 = "uuid3";
    private static final String FULLNAME_3 = "fullName_3";
    private static final String UUID_4 = "uuid4";
    private static final String FULLNAME_4 = "fullName_4";
    private static final String UUID_31 = "uuid3";
    private static final String FULLNAME_31 = "fullName_3";
    private static final String UUID_NOT_EXIST = "dummy";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_4 = new Resume(UUID_4);
    private static final Resume RESUME_31 = new Resume(UUID_31);


    public AbstractStorageTest(final Storage storage) {
        this.storage = storage;
    }


    @Before
    public void setUp() {
        storage.clear();
        RESUME_1.setFullName(FULLNAME_1);
        RESUME_2.setFullName(FULLNAME_2);
        RESUME_3.setFullName(FULLNAME_3);
        RESUME_4.setFullName(FULLNAME_4);
        RESUME_31.setFullName(FULLNAME_31);

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
        assertSame(r, storage.get(r.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXIST);
    }

    @Test
    public void update() {
        storage.update(RESUME_31);
        assertSame(RESUME_31, storage.get(RESUME_31.getUuid()));
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
        storage.save(new Resume(UUID_31));
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
        assertArrayEquals(new Resume[]{RESUME_2, RESUME_1, RESUME_3}, storage.getAllSorted().toArray());
    }

}