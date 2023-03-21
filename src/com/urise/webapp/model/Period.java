package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private String description;

    public Period(final LocalDate startDate, final LocalDate endDate, final String title) {
        Objects.requireNonNull(startDate, "startDate organization must not be null");
        Objects.requireNonNull(endDate, "endDate organization must not be null");
        Objects.requireNonNull(title, "title organization must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
    }

    public Period(final LocalDate startDate, final LocalDate endDate, final String title, final String description) {
        Objects.requireNonNull(startDate, "startDate organization must not be null");
        Objects.requireNonNull(endDate, "endDate organization must not be null");
        Objects.requireNonNull(title, "title organization must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Period period = (Period) o;

        if (!getStartDate().equals(period.getStartDate())) return false;
        if (!getEndDate().equals(period.getEndDate())) return false;
        if (!getTitle().equals(period.getTitle())) return false;
        return getDescription() != null ? getDescription().equals(period.getDescription()) : period.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = getStartDate().hashCode();
        result = 31 * result + getEndDate().hashCode();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return startDate + " - " + endDate + ": " + title + '\n' +
                description + '\n';
    }
}
