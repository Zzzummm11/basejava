import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    private int count;

    void clear() {
        count = 0;
    }

    void save(Resume r) {
        storage[count++] = r;

    }

    Resume get(String uuid) {
        for (int i = 0; i < count; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int index = 0;
        for (int i = 0; i < count; i++) {
            if (uuid.equals(storage[i].uuid)) {
                index = i;
            }
        }
        if (index < count - 1) {
            System.arraycopy(storage, index + 1, storage, index, count - 1 - index);
        }
        count--;

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {

        return Arrays.copyOf(storage, count);
    }

    int size() {
        return count;
    }
}
