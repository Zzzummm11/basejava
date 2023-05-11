package com.urise.webapp;

import com.urise.webapp.model.ListTextSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.TextSection;

import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestData {

    public static Resume createResume(String uuid, String fullName) {
        Resume r = new Resume(uuid,fullName);
        addContactsToResume(r);
        addSectionsToResume(r);
        return r;
    }

    public static Resume createResume(String fullName) {
        Resume r = new Resume(fullName);
        addContactsToResume(r);
        addSectionsToResume(r);
        return r;
    }


    private static void addContactsToResume(Resume r){
        r.getContacts().put(TELEPHONE, "telephone");
        r.getContacts().put(SKYPE, "skype");
        r.getContacts().put(EMAIL, "email");
        r.getContacts().put(LINKEDIN, "linkedin");
        r.getContacts().put(GITHUB, "github");
        r.getContacts().put(STACKOVERFLOW, "stackoverflow");
        r.getContacts().put(WEBSITE, "website");
    }

    private static void addSectionsToResume(Resume r){
        r.getSections().put(OBJECTIVE, new TextSection("Objective - description"));
        r.getSections().put(PERSONAL, new TextSection("Personal - description"));

        List<String> achievementList = new ArrayList<>();
        r.getSections().put(ACHIEVEMENT, new ListTextSection(achievementList));
        achievementList.add("Achievement_1");
        achievementList.add("Achievement_2");
        achievementList.add("Achievement_3");

        List<String> qualificationList = new ArrayList<>();
        r.getSections().put(QUALIFICATION, new ListTextSection(qualificationList));
        qualificationList.add("Qualification_1");
        qualificationList.add("Qualification_2");
        qualificationList.add("Qualification_3");
        qualificationList.add("Qualification_4");
/*
        List<Organization> allOrganizations = new ArrayList<>();

        r.getSections().put(EXPERIENCE, new OrganizationSection(allOrganizations));
        List<Period> periods1 = new ArrayList<>();
        allOrganizations.add(new Organization("Alcatel", periods1));
        periods1.add(new Period(DataUtil.of(1997, Month.SEPTEMBER), DataUtil.of(2005, Month.JANUARY), "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)"));
        List<Period> periods2 = new ArrayList<>();
        allOrganizations.add(new Organization("Siemens AG", "https://www.siemens.com/global/en.html", periods2));
        periods2.add(new Period(DataUtil.of(2005, Month.JANUARY), DataUtil.of(2007, Month.FEBRUARY), "Разработчик ПО",
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)"));

        List<Organization> allEducations = new ArrayList<>();

        r.getSections().put(EDUCATION, new OrganizationSection(allEducations));
        List<Period> periods11 = new ArrayList<>();
        allEducations.add(new Organization("Coursera", "https://www.coursera.org/learn/scala-functional-programming", periods11));
        periods11.add(new Period(DataUtil.of(2013, Month.MARCH), DataUtil.of(2013, Month.MAY), "\n" +
                "'Functional Programming Principles in Scala' by Martin Odersky"));
        List<Period> periods22 = new ArrayList<>();
        allEducations.add(new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", periods22));
        periods22.add(new Period(DataUtil.of(2011, Month.MARCH),DataUtil.of(2011, Month.APRIL), "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML'","dsdsdsd"));

    */
    }

}
