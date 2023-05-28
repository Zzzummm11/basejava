package com.urise.webapp.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListTextSection extends AbstractSection implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> list;

    public ListTextSection() {
    }

    public ListTextSection(final List<String> list) {
        Objects.requireNonNull(list, "items must not be null");
        this.list = list;
    }
    public ListTextSection(String... items) {
        this(Arrays.asList(items));
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ListTextSection that = (ListTextSection) o;

        return getList().equals(that.getList());
    }

    @Override
    public int hashCode() {
        return getList().hashCode();
    }

    @Override
    public String toString() {
        return String.join("\n", list);
    }
}
