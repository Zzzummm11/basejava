<%@ page import="com.urise.webapp.model.ListTextSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/theme/${theme}.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/view-resume-styles.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="header"/>
<div class="scrollable-panel">
    <div class="form-wrapper">

        <div class="full-name">${resume.fullName}
            <a class="no-underline-anchor" href="resume?uuid=${resume.uuid}&action=edit&theme=${theme}">
                <img src="img/${theme}/edit.svg" alt="">
            </a>
        </div>
        <div class="contacts">
            <c:forEach var="contactEntry" items="${resume.contacts}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
                <div><%=contactEntry.getKey().toHtml(contactEntry.getValue())%>
                </div>
            </c:forEach>
        </div>

        <div class="spacer"></div>

        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType,
                         com.urise.webapp.model.AbstractSection>"/>

            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>

            <div class="section">${type.title}</div>
            <c:choose>
                <c:when test="${type=='OBJECTIVE'}">
                    <div class="position"><%=section.toString()%>
                    </div>
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <div class="qualities"><%=section.toString()%>
                    </div>
                </c:when>
                <c:when test="${type=='QUALIFICATION' || type=='ACHIEVEMENT'}">
                    <ul class="list">
                        <c:forEach var="item" items="<%=((ListTextSection) section).getList()%>">
                            <li>${item}</li>
                        </c:forEach>
                    </ul>
                </c:when>

                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="org" items="<%=((OrganizationSection) section).getAllOrganizations()%>">
                        <div class="section-wrapper">

                            <c:choose>
                                <c:when test="${empty org.website}">
                                    <div class="job-name">${org.name}</div>
                                </c:when>
                                <c:otherwise>
                                    <div class="job-name"><a class="contact-link"
                                                             href="${org.website}">${org.name}</a></div>
                                </c:otherwise>
                            </c:choose>

                            <c:forEach var="period" items="${org.periods}">
                                <jsp:useBean id="period" type="com.urise.webapp.model.Period"/>
                                <div class="period-position">
                                    <div class="period"><%=HtmlUtil.formatDates(period)%>
                                    </div>
                                    <div class="position">${period.title}</div>
                                </div>
                                <c:choose>
                                    <c:when test="${empty period.description}">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="description">${period.description}</div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <div class="footer-spacer"></div>
    </div>
</div>
<jsp:include page="footer"/>
</body>
</html>
