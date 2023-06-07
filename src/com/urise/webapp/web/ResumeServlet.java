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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.model.SectionType.*;
import static org.apache.taglibs.standard.functions.Functions.trim;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init() throws ServletException {
        storage = Config.get().getStorage();
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
            String[] organizationNames = request.getParameterValues(sectionType.name() + "organizationName");
            AbstractSection section;
            if (sectionValue != null && sectionValue.trim().length() != 0 || organizationNames != null && organizationNames.length > 0) {
                section = switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> new TextSection(trim(sectionValue.replaceAll("\\s+", " ")));
                    case ACHIEVEMENT, QUALIFICATION -> {
                        List<String> list = Arrays.asList(sectionValue.replaceAll("(?m)^[ \t]*\r?\n", "").split("\n"));
                        list.replaceAll(Functions::trim);
                        yield new ListTextSection(list);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> allOrganisations = new ArrayList<>();
                        String[] organizationWebsite = request.getParameterValues(sectionType.name() + "website");
                        for (int i = 0; i < organizationNames.length; i++) {
                            String organizationName = organizationNames[i];
                            if (organizationName != null && organizationName.trim().length() != 0) {
                                List<Period> periodList = new ArrayList<>();
                                String[] periods = request.getParameterValues(sectionType.name() + i);
                                String[] dataStart = request.getParameterValues(sectionType.name() + i + "startDate");
                                String[] dataEnd = request.getParameterValues(sectionType.name() + i + "endDate");
                                String[] title = request.getParameterValues(sectionType.name() + i + "title");
                                String[] description = request.getParameterValues(sectionType.name() + i + "description");
                                if (periods != null) {
                                    for (int j = 0; j < periods.length; j++) {
                                        if (dataStart[j] != null && dataStart[j].trim().length() != 0 && LocalDate.parse(dataStart[j]) != LocalDate.MIN &&
                                                dataEnd[j] != null && dataEnd[j].trim().length() != 0 && LocalDate.parse(dataEnd[j]) != LocalDate.MIN) {
                                            Period period = new Period(LocalDate.parse(dataStart[j]), LocalDate.parse(dataEnd[j]),
                                                    trim(title[j]), description[j].replaceAll("(?m)^[ \t]*\r?\n", ""));
                                            periodList.add(period);
                                        }
                                    }
                                }
                                if (organizationWebsite[i] != null && organizationWebsite[i].trim().length() != 0) {
                                    allOrganisations.add(new Organization(trim(organizationName), trim(organizationWebsite[i]), periodList));
                                } else {
                                    allOrganisations.add(new Organization(organizationName, periodList));
                                }

                            }
                        }
                        if (allOrganisations.size() > 0) {
                            yield new OrganizationSection(allOrganisations);
                        } else {
                            yield OrganizationSection.EMPTY;
                        }
                    }

                };
                r.addSection(sectionType, section);
            } else {
                r.getSections().remove(sectionType);
            }
        }
        storage.update(r);
        response.sendRedirect("resume");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
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
                            if (section == null) {
                                section = OrganizationSection.EMPTY;
                            } else {
                                List<Organization> allOrganizations = new ArrayList<>();
                                for (Organization organization : ((OrganizationSection) section).getAllOrganizations()) {

                                        if (organization.getPeriods() == null) {
                                            organization.setPeriods(List.of(Period.EMPTY));
                                        }
                                        allOrganizations.add(organization);

                                }
                                section = new OrganizationSection(allOrganizations);
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
}
