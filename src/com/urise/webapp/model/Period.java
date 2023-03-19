package com.urise.webapp.model;

import java.time.LocalDate;

public class Period {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private String description;

    public Period(final LocalDate startDate, final LocalDate endDate, final String title) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
    }

    public Period(final LocalDate startDate, final LocalDate endDate, final String title, final String description) {
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

        if (getStartDate() != null ? !getStartDate().equals(period.getStartDate()) : period.getStartDate() != null)
            return false;
        if (getEndDate() != null ? !getEndDate().equals(period.getEndDate()) : period.getEndDate() != null)
            return false;
        if (getTitle() != null ? !getTitle().equals(period.getTitle()) : period.getTitle() != null) return false;
        return getDescription() != null ? getDescription().equals(period.getDescription()) : period.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = getStartDate() != null ? getStartDate().hashCode() : 0;
        result = 31 * result + (getEndDate() != null ? getEndDate().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return startDate + " - " + endDate + ": " + title + '\n' +
                description + '\n';
    }
}
