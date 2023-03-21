package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;


public class ResumeTestData {
    public static void main(String[] args) {

        Resume r1 = new Resume("Григорий Кислин");

        r1.getContacts().put(TELEPHONE, "+7(921) 855-0482");
        r1.getContacts().put(SKYPE, "skype:grigory.kislin");
        r1.getContacts().put(EMAIL, "gkislin@yandex.ru");
        r1.getContacts().put(LINKEDIN, "https://www.linkedin.com/");
        r1.getContacts().put(GITHUB, "https://github.com/gkislin");
        r1.getContacts().put(STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        r1.getContacts().put(WEBSITE, "http://gkislin.ru/");


        r1.getSections().put(OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и " +
                "Enterprise технологиям"));
        r1.getSections().put(PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры."));

        List<String> achievementList = new ArrayList<>();
        r1.getSections().put(ACHIEVEMENT, new ListTextSection(achievementList));
        achievementList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков");
        achievementList.add("С 2013 года: разработка проектов \"Разработка Web приложения\"");
        achievementList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike");

        List<String> qualificationList = new ArrayList<>();
        r1.getSections().put(QUALIFICATION, new ListTextSection(qualificationList));
        qualificationList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualificationList.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");

        List<Organization> allOrganisations = new ArrayList<>();

        r1.getSections().put(EXPERIENCE, new OrganizationSection(allOrganisations));
        List<Period> periods1 = new ArrayList<>();
        allOrganisations.add(new Organization("Alcatel", "http://www.alcatel.ru/", periods1));
        periods1.add(new Period(LocalDate.of(1997, 9, 1), LocalDate.of(2005, 1, 1), "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)"));
        List<Period> periods2 = new ArrayList<>();
        allOrganisations.add(new Organization("Siemens AG", "https://www.siemens.com/global/en.html", periods2));
        periods2.add(new Period(LocalDate.of(2005, 1, 1), LocalDate.of(2007, 2, 1), "Разработчик ПО",
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)"));

        List<Organization> allEducations = new ArrayList<>();

        r1.getSections().put(EDUCATION, new OrganizationSection(allEducations));
        List<Period> periods11 = new ArrayList<>();
        allEducations.add(new Organization("Coursera", "https://www.coursera.org/learn/scala-functional-programming", periods11));
        periods11.add(new Period(LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1), "\n" +
                "'Functional Programming Principles in Scala' by Martin Odersky"));
        List<Period> periods22 = new ArrayList<>();
        allEducations.add(new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", periods22));
        periods22.add(new Period(LocalDate.of(2011, 3, 1), LocalDate.of(2011, 4, 1), "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML'"));


        System.out.println(r1.getFullName());

        for (ContactType type : ContactType.values()) {
            System.out.println(type.getTitle() + r1.getContacts().get(type));
        }
        System.out.println("______________________________");

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle() + '\n' + r1.getSections().get(type) + '\n');
        }
    }
}