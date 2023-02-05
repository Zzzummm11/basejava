package com.urise.webapp.exeption;

/**
 * @author devonline
 * @link http://devonline.academy/java
 */
public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(final String message, final String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
