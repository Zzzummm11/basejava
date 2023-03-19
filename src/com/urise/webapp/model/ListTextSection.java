package com.urise.webapp.model;

import java.util.List;

public class ListTextSection extends AbstractSection {
    private final List<String> list;

    public ListTextSection(final List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ListTextSection that = (ListTextSection) o;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append("- ").append(s).append('\n');
        }
        return sb.toString();
    }
}
