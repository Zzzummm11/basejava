package com.urise.webapp.exeption;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(final String uuid) {
        super("Resume " + uuid + " not exist", uuid);
    }
}
