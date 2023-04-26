package com.urise.webapp.exeption;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(final String message, final String uuid) {
        super(message);
        this.uuid = uuid;
    }


    public StorageException(final String message, final String uuid, final Exception e) {
        super(message, e);
        this.uuid = uuid;
    }

    public StorageException(Exception e) {
        this(e.getMessage(), String.valueOf(e));
    }

    public String getUuid() {
        return uuid;
    }
}
