package com.urise.webapp.model;

import java.util.List;

public class OrganizationSection extends AbstractSection {
    private final List<Organization> allOrganisations;

    public OrganizationSection(final List<Organization> allOrganisations) {
        this.allOrganisations = allOrganisations;
    }

    public List<Organization> getAllOrganisations() {
        return allOrganisations;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final OrganizationSection that = (OrganizationSection) o;

        return getAllOrganisations() != null ? getAllOrganisations().equals(that.getAllOrganisations()) : that.getAllOrganisations() == null;
    }

    @Override
    public int hashCode() {
        return allOrganisations.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (Organization allOrganisation : allOrganisations) {
            sb.append(allOrganisation).append('\n');
        }
        return sb.toString();
    }
}
