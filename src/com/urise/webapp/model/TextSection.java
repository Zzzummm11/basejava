package com.urise.webapp.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;


public class TextSection extends AbstractSection implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public static final TextSection EMPTY = new TextSection("");
    private String text;

    public TextSection() {
    }

    public TextSection(final String textSection) {
        Objects.requireNonNull(textSection, "content must not be null");
        this.text = textSection;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final TextSection that = (TextSection) o;

        return getText().equals(that.getText());
    }

    @Override
    public int hashCode() {
        return getText().hashCode();
    }

    @Override
    public String toString() {
        return text;
    }
}
