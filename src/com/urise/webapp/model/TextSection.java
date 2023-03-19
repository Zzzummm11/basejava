package com.urise.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private final String text;


    public TextSection(final String textSection) {
        this.text = textSection;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final TextSection that = (TextSection) o;

        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }

    @Override
    public String toString() {
        return text;
    }
}
