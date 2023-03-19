package com.urise.webapp.model;

public enum ContactType {

    TELEPHONE("Тел.: "),
    SKYPE("Skype: "),
    EMAIL("Почта: "),
    LINKEDIN("Профиль LinkedIn "),
    GITHUB("Профиль GitHub "),
    STACKOVERFLOW("Профиль Stackoverflow "),
    WEBSITE("Домашняя страница ");

    private final String title;

    ContactType(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
