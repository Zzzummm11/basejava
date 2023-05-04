package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DataUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestData {

    public static Resume createResume(String uuid, String fullName) {
        Resume r = new Resume(uuid,fullName);
        addContactsToResume(r);
        return r;
    }

    public static Resume createResume(String fullName) {
        Resume r = new Resume(fullName);
        addContactsToResume(r);
        return r;
    }


    private static void addContactsToResume(Resume r){
        r.getContacts().put(TELEPHONE, "+7(921) 855-0482");
        r.getContacts().put(SKYPE, "skype:grigory.kislin");
        r.getContacts().put(EMAIL, "gkislin@yandex.ru");
        r.getContacts().put(LINKEDIN, "https://www.linkedin.com/");
        r.getContacts().put(GITHUB, "https://github.com/gkislin");
        r.getContacts().put(STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        r.getContacts().put(WEBSITE, "http://gkislin.ru/");
    }

    private static void addSectionsToResume(Resume r){
        r.getSections().put(OBJECTIVE, new TextSection("hj"));
        r.getSections().put(PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры."));

        List<String> achievementList = new ArrayList<>();
        r.getSections().put(ACHIEVEMENT, new ListTextSection(achievementList));
        achievementList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков");
        achievementList.add("С 2013 года: разработка проектов \"Разработка Web приложения\"");
        achievementList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike");

        List<String> qualificationList = new ArrayList<>();
        r.getSections().put(QUALIFICATION, new ListTextSection(qualificationList));
        qualificationList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualificationList.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");

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
    }
}
