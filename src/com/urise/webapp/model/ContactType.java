package com.urise.webapp.model;

public enum ContactType {

    TELEPHONE("���.: "),
    SKYPE("Skype: "),
    EMAIL("�����: "),
    LINKEDIN("������� LinkedIn "),
    GITHUB("������� GitHub "),
    STACKOVERFLOW("������� Stackoverflow "),
    WEBSITE("�������� �������� ");

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
