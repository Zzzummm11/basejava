package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private List<Organization> allOrganizations;

    public OrganizationSection() {
    }

    public OrganizationSection(final List<Organization> allOrganisations) {
        Objects.requireNonNull(allOrganisations, "organizations must not be null");
        this.allOrganizations = allOrganisations;
    }

    public List<Organization> getAllOrganizations() {
        return allOrganizations;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final OrganizationSection that = (OrganizationSection) o;

        return getAllOrganizations().equals(that.getAllOrganizations());
    }

    @Override
    public int hashCode() {
        return getAllOrganizations().hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
         {
            sb.append(allOrganizations).append('\n');
        }
        return sb.toString();
    }
}
