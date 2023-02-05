package com.urise.webapp.exeption;

/**
 * @author devonline
 * @link http://devonline.academy/java
 */
public class ExistStorageException extends StorageException {
    public ExistStorageException(final String uuid) {
        super("Resume " + uuid + " already exist", uuid);
    }
}
