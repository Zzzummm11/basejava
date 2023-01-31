package com.urise.webapp.model;

public class Resume implements Comparable<Resume> {
    private final String uuid;

    public Resume(final String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int compareTo(final Resume o) {
        return uuid.compareTo(o.uuid);
    }
}
