package com.urise.webapp.storage;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class PathStorage extends AbstractStorage<Path> {
    private Stream stream;
    private final Path directory;

    public void setStream(final Stream stream) {
        this.stream = stream;
    }
    public Stream getStream() {
        return stream;
    }

    public Path getDirectory() {
        return directory;
    }

    protected PathStorage(final String dir, final Stream stream) {
        directory = Paths.get(dir);
        this.stream = stream;
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(stream, "stream must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected boolean isExist(final Path path) {
        return Files.exists(path);
    }

    @Override
    protected Path getSearchKey(final String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doSave(final Resume r, final Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create path ", r.getUuid(), e);
        }
        doUpdate(path, r);
    }

    @Override
    protected Resume doGet(final Path path) {
        try {
            return getStream().doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", null);
        }
    }

    @Override
    protected void doUpdate(final Path path, final Resume r) {
        try {
            getStream().doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    protected void doDelete(final Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path is not deleted", null);
        }
    }

    @Override
    protected List<Resume> convertToList() {
        try {
            return Files.list(directory).map(this::doGet)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Path is null", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Path is null", null);
        }

    }


    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }
}