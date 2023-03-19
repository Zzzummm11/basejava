package com.urise.webapp.model;

public enum SectionType {

    OBJECTIVE("Позиция: "),
    PERSONAL("Личные качества: "),
    ACHIEVEMENT("Достижения: "),
    QUALIFICATION("Квалификация: "),
    EXPERIENCE("Опыт работы: "),
    EDUCATION("Образование: ");

    private final String title;

    SectionType(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
