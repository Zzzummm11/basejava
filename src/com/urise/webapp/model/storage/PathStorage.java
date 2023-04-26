package com.urise.webapp.model.storage;

import com.urise.webapp.exeption.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.storage.stream.StreamSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PathStorage extends AbstractStorage<Path> {
    private StreamSerializer stream;
    private final Path directory;

    public void setStream(final StreamSerializer stream) {
        this.stream = stream;
    }

    public StreamSerializer getStream() {
        return stream;
    }

    public Path getDirectory() {
        return directory;
    }

    public PathStorage(final String dir, final StreamSerializer stream) {
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
            return stream.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", null);
        }
    }

    @Override
    protected void doUpdate(final Path path, final Resume r) {
        try {
            stream.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
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
        return getFilesList().map(this::doGet)
                .collect(Collectors.toList());
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }


    @Override
    public void clear() {
        getFilesList().forEach(this::doDelete);
    }

    public Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }

    }
}