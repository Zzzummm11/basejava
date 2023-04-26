package com.urise.webapp.model.storage;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.storage.stream.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private StreamSerializer stream;
    private final File directory;


    public void setStream(final StreamSerializer stream) {
        this.stream = stream;
    }

    public StreamSerializer getStream() {
        return stream;
    }

    public File getDirectory() {
        return directory;
    }

    public FileStorage(final File directory, final StreamSerializer stream) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(stream, "stream must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.stream = stream;
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
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(file, r);
    }

    @Override
    protected Resume doGet(final File file) {
        try {
            return stream.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(final File file, final Resume r) {
        try {
            stream.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", r.getUuid(), e);
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
        for (File file : getListFiles()) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    public int size() {
        return getListFiles().length;
    }


    @Override
    public void clear() {
        for (File file : getListFiles()) {
            doDelete(file);
        }
    }

    public File[] getListFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("File is null", null);
        }
        return files;
    }
}