package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import org.apache.taglibs.standard.functions.Functions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static com.urise.webapp.model.SectionType.*;
import static org.apache.taglibs.standard.functions.Functions.trim;

public class ResumeServlet extends HttpServlet {

    private boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    private enum THEME {
        dark, light
    }

    private Storage storage;
    private final Set<String> themes = new HashSet<>();

    @Override
    public void init() throws ServletException {
        storage = Config.get().getStorage();
        for (THEME t : THEME.values()) {
            themes.add(t.name());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = trim(request.getParameter("fullName"));
        try {
            storage.get(uuid);
        } catch (NotExistStorageException e) {
            storage.save(new Resume(uuid, fullName));
        }
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());

            if (value != null && value.trim().length() != 0) {
                r.addContact(type, trim(value));
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType sectionType : SectionType.values()) {
            String sectionValue = request.getParameter(sectionType.name());
            String[] organizationNames = request.getParameterValues(sectionType.name());
            if (isEmpty(sectionValue) || organizationNames == null || organizationNames.length < 1) {
                r.getSections().remove(sectionType);
            } else {
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE ->
                            r.addSection(sectionType, new TextSection(trim(sectionValue.replaceAll("\\s+", " "))));
                    case ACHIEVEMENT, QUALIFICATION -> {
                        final List<String> list = Arrays.asList(sectionValue.replaceAll("(?m)^[ \t]*\r?\n", "").split("\n"));
                        list.replaceAll(Functions::trim);
                        r.addSection(sectionType, new ListTextSection(list));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        final List<Organization> allOrganisations = new ArrayList<>();
                        final String[] organizationWebsite = request.getParameterValues(sectionType.name() + "website");
                        for (int i = 0; i < organizationNames.length; i++) {
                            String name = organizationNames[i];
                            if (!isEmpty(name)) {
                                final List<Period> periods = new ArrayList<>();
                                String prefix = sectionType.name() + i;
                                String[] startDates = request.getParameterValues(prefix + "startDate");
                                String[] endDates = request.getParameterValues(prefix + "endDate");
                                String[] title = request.getParameterValues(prefix + "title");
                                String[] description = request.getParameterValues(prefix + "description");
                                for (int j = 0; j < title.length; j++) {
                                    if (!isEmpty(startDates[j]) && !isEmpty(endDates[j]) && !isEmpty(title[j])) {
                                        periods.add(new Period(
                                                LocalDate.parse(startDates[j]),
                                                LocalDate.parse(endDates[j]),
                                                trim(title[j]),
                                                trim(description[j].replaceAll("(?m)^[ \t]*\r?\n", ""))
                                        ));
                                    }
                                }
                                if (!isEmpty(organizationWebsite[i])) {
                                    allOrganisations.add(new Organization(name, organizationWebsite[i], periods));
                                } else {
                                    allOrganisations.add(new Organization(name, periods));
                                }

                            }
                        }
                        r.addSection(sectionType, new OrganizationSection(allOrganisations));
                    }
                }
            }
        }
        storage.update(r);
        response.sendRedirect("resume?theme=" + getTheme(request));
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        request.setAttribute("theme", getTheme(request));
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("all_resumes").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
            }
            case "view" -> {
                r = storage.get(uuid);
                request.setAttribute("resume", r);
                request.getRequestDispatcher("view")
                        .forward(request, response);
            }
            case "edit" -> {
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATION:
                            if (section == null) {
                                section = ListTextSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationSection organizationSection = (OrganizationSection) section;
                            final List<Organization> emptyFirstOrganizations = new ArrayList<>();
                            if (organizationSection != null) {
                                final List<Organization> organizations = organizationSection.getAllOrganizations();
                                for (Organization org : organizations) {
                                    final List<Period> emptyFirstPeriods = new ArrayList<>();
                                    emptyFirstPeriods.add(Period.EMPTY);
                                    emptyFirstPeriods.addAll(org.getPeriods());
                                    emptyFirstOrganizations.add(new Organization(org.getName(), org.getWebsite(), emptyFirstPeriods));
                                }
                                section = new OrganizationSection(emptyFirstOrganizations);
                            } else {
                                section = OrganizationSection.EMPTY;
                            }
                            break;
                    }
                    r.addSection(type, section);
                }
                request.setAttribute("resume", r);
                request.getRequestDispatcher("edit")
                        .forward(request, response);
            }

            case "add" -> {
                Resume newResume = new Resume();
                newResume.addSection(OBJECTIVE, TextSection.EMPTY);
                newResume.addSection(PERSONAL, TextSection.EMPTY);
                newResume.addSection(ACHIEVEMENT, ListTextSection.EMPTY);
                newResume.addSection(QUALIFICATION, ListTextSection.EMPTY);
                newResume.addSection(EXPERIENCE, OrganizationSection.EMPTY);
                newResume.addSection(EDUCATION, OrganizationSection.EMPTY);

                request.setAttribute("resume", newResume);
                request.getRequestDispatcher("edit").forward(request, response);
            }
            default -> throw new IllegalArgumentException("Action" + action + " is illegal");
        }
    }

    private String getTheme(HttpServletRequest request) {
        String theme = request.getParameter("theme");
        return themes.contains(theme) ? theme : THEME.light.name();
    }
}
