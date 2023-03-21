package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {

    private final String name;
    private String website;
    private final List<Period> periods;


    public Organization(final String name, final List<Period> periods) {
        Objects.requireNonNull(name, "name organization must not be null");
        this.name = name;
        this.periods = periods;
    }

    public Organization(final String name, final String website, final List<Period> periods) {
        Objects.requireNonNull(name, "name organization must not be null");
        this.name = name;
        this.website = website;
        this.periods = periods;
    }

    public void setWebsite(final String website) {
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Organization that = (Organization) o;

        if (!getName().equals(that.getName())) return false;
        if (getWebsite() != null ? !getWebsite().equals(that.getWebsite()) : that.getWebsite() != null) return false;
        return getPeriods() != null ? getPeriods().equals(that.getPeriods()) : that.getPeriods() == null;
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + (getWebsite() != null ? getWebsite().hashCode() : 0);
        result = 31 * result + (getPeriods() != null ? getPeriods().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (Period period : periods) {
            sb.append(period);
        }
        return name + ", " + website + ", " + '\n' +
                sb;
    }
}
