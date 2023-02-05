package com.urise.webapp.exeption;

/**
 * @author devonline
 * @link http://devonline.academy/java
 */
public class NotExistStorageException extends StorageException {
    public NotExistStorageException(final String uuid) {
        super("Resume " + uuid + " not exist", uuid);
    }
}
