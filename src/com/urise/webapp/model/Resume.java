package com.urise.webapp.model;

public class Resume {
    private final String uuid;

    public Resume(final String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return uuid;
    }
}
