package com.urise.webapp.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract boolean isExist(final SK searchKey);

    protected abstract SK getSearchKey(final String uuid);

    protected abstract void doSave(final Resume r, final SK searchKey);

    protected abstract Resume doGet(final SK searchKey);

    protected abstract void doUpdate(final SK searchKey, final Resume r);

    protected abstract void doDelete(final SK searchKey);

    protected abstract List<Resume> convertToList();


    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            return searchKey;
        } else {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    public void save(final Resume r) {
        LOG.info("Save " + r);
        SK searchKey = getNotExistingSearchKey(r.getUuid());
        doSave(r, searchKey);
    }

    public Resume get(final String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public void update(final Resume r) {
        LOG.info("Update " + r);
        SK searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(searchKey, r);

    }

    public void delete(final String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    public static final Comparator<Resume> COMPARE_RESUME = Comparator.comparing((Resume r) -> r.getFullName())
            .thenComparing((Resume r) -> r.getUuid());

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = convertToList();
        list.sort(COMPARE_RESUME);
        return list;
    }

}
