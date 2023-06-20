package com.urise.webapp.model;

public enum ContactType {

    TELEPHONE("Тел. "),
    SKYPE("Skype "),
    EMAIL("Email ") {
        @Override
        public String toHtml0(String value) {
            return "<a class=\"contact-link\" href='mailto: " + value + "'>" + value + "</a>";
        }
    },

    LINKEDIN("LinkedIn "),
    GITHUB("GitHub "),
    STACKOVERFLOW("Stackoverflow "),
    WEBSITE("Домашняя страница");

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

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }
}
