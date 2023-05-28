package com.urise.webapp.model;

public enum SectionType {

    OBJECTIVE("Позиция: ") {
        @Override
        public String toHtml0(AbstractSection section) {
            return ((TextSection) section).getText();
        }
    },
    PERSONAL("Личные качества: ") {
        @Override
        public String toHtml0(AbstractSection section) {
            return ((TextSection) section).getText();
        }
    },
    ACHIEVEMENT("Достижения: ") {
        @Override
        public String toHtml0(AbstractSection section) {
            return String.join("<br/>", ((ListTextSection) section).getList());
        }
    },
    QUALIFICATION("Квалификация: ") {
        @Override
        public String toHtml0(AbstractSection section) {
            return String.join("<br/>", ((ListTextSection) section).getList());
        }
    },
    EXPERIENCE("Опыт работы: ") {
        @Override
        public String toHtml0(AbstractSection section) {
            return OrganizationSectionToHtml((OrganizationSection) section);
        }
    },

    EDUCATION("Образование: ") {
        @Override
        public String toHtml0(AbstractSection section) {
            return OrganizationSectionToHtml((OrganizationSection) section);
        }
    };

    private static String OrganizationSectionToHtml(final OrganizationSection section) {
        final StringBuilder sb = new StringBuilder();
        for (Organization organization : section.getAllOrganizations()) {
            if (organization.getWebsite() != null) {
                sb.append("<a href=" + organization.getWebsite() + ">" + organization.getName() + "</a></br>");
            } else {
                sb.append(organization.getName() + "</br>");
            }
            for (Period period : organization.getPeriods()) {
                sb.append("<b>" + period.getStartDate() + " - " + period.getEndDate() + "</b>" + ": " + period.getTitle() + "<br/>");
                sb.append(period.getDescription() + "<br/>");
            }
            sb.append("<br/>");
        }
        return sb.toString();
    }

    private final String title;

    SectionType(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(AbstractSection section) {
        return getTitle() + ": " + section;
    }

    public String toHtml(AbstractSection section) {
        return (section == null) ? "" : toHtml0(section);
    }
}
