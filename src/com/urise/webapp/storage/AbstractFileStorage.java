package com.urise.webapp.storage;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected abstract void doWrite(final Resume r, final File file) throws IOException;

    protected abstract Resume doRead(final File file) throws IOException;


    protected AbstractFileStorage(final File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
        this.directory = directory;
    }

    public File getDirectory() {
        return directory;
    }

    @Override
    protected boolean isExist(final File file) {
        return file.exists();
    }

    @Override
    protected File getSearchKey(final String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doSave(final Resume r, final File file) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(final File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(final File file, final Resume r) {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(final File file) {
        if (!file.delete()) {
            throw new StorageException("File is not deleted", file.getName());
        }
    }

    @Override
    protected List<Resume> convertToList() {
        List<Resume> list = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("File is null", null);
        }
        for (File file : files) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("File is null", null);
        }
        return files.length;
    }


    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("File is null", null);
        }
        for (File file : files) {
            doDelete(file);
        }
    }
}