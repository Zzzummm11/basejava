package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

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
        String fullName = request.getParameter("fullName");
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
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType sectionType : SectionType.values()) {
            String sectionValue = request.getParameter(sectionType.name());
//            String organizationName = request.getParameter("organization.name-0");
//            int organizationCount;
//            if (request.getParameter("organizationCount") != null) {
//                organizationCount = Integer.parseInt(request.getParameter("organizationCount"));
//            } else {
//                organizationCount = 0;
//            }

            if (sectionValue != null && sectionValue.trim().length() != 0
//                    || organizationName != null && organizationName.trim().length() != 0
            ) {
                AbstractSection section = switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> new TextSection(sectionValue);
                    case ACHIEVEMENT, QUALIFICATION -> new ListTextSection(Arrays.asList(sectionValue.split("\n")));
//                    case EXPERIENCE, EDUCATION -> {
//                        List<Organization> allOrganisations = new ArrayList<>();
//                        for (int i = 0; i < organizationCount; i++) {
//                           organizationName = request.getParameter("organization.name-" + i);
//                            String organizationWebsite = request.getParameter("organization.website-" + i);
//
//                            int periodCount;
//                            if (request.getParameter("periodCount" + i) != null) {
//                                periodCount = Integer.parseInt(request.getParameter("periodCount" + i));
//                            } else {
//                                periodCount = 0;
//                            }
//
//                            List<Period> periods = new ArrayList<>();
//                            if (periodCount > 0) {
//                                for (int j = 0; j < periodCount; j++) {
//                                    String dataStart = request.getParameter("period.startDate-" + i + j);
//                                    String dataEnd = request.getParameter("period.endDate-" + i + j);
//                                    String title = request.getParameter("period.title-" + i + j);
//                                    String description = request.getParameter("period.description-" + i + j);
//
//                                    Period period = new Period(LocalDate.parse(Objects.requireNonNull(dataStart)),
//                                            LocalDate.parse(Objects.requireNonNull(dataEnd)), title);
//
//                                    period.setDescription(description);
//                                    periods.add(period);
//                                }
//                            }
//                            if (organizationWebsite != null && organizationWebsite.trim().length() != 0) {
//                                allOrganisations.add(new Organization(organizationName, organizationWebsite, periods));
//                            } else {
//                                allOrganisations.add(new Organization(organizationName, periods));
//                            }
//                        }
//                        yield new OrganizationSection(allOrganisations);
//                    }
                    case EXPERIENCE -> null;
                    case EDUCATION -> null;
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
                request.setAttribute("resume", r);
//                if (r.getSection(EXPERIENCE) == null) {
//                    r.addSection(EXPERIENCE, new OrganizationSection(new ArrayList<>(List.of(new Organization()))));
//                }
//                if (r.getSection(EDUCATION) == null) {
//                    r.addSection(EDUCATION, new OrganizationSection(new ArrayList<>(List.of(new Organization()))));
//                }
//                request.setAttribute("experience", r.getSection(EXPERIENCE));
//                request.setAttribute("education", r.getSection(EDUCATION));
                request.getRequestDispatcher("edit")
                        .forward(request, response);
            }

            case "add" -> {
                Resume newResume = new Resume();
//                newResume.addSection(EXPERIENCE, new OrganizationSection(new ArrayList<>(List.of(new Organization()))));
//                newResume.addSection(EDUCATION, new OrganizationSection(new ArrayList<>(List.of(new Organization()))));
                request.setAttribute("resume", newResume);
//                request.setAttribute("experience", newResume.getSection(EXPERIENCE));
//                request.setAttribute("education", newResume.getSection(EDUCATION));
                request.getRequestDispatcher("edit").forward(request, response);
            }
            default -> throw new IllegalArgumentException("Action" + action + " is illegal");
        }
    }
}
