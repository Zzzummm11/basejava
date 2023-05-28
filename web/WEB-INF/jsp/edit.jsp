<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
<%--<jsp:useBean id="education" type="com.urise.webapp.model.OrganizationSection" scope="request"/>--%>
<%--<jsp:useBean id="experience" type="com.urise.webapp.model.OrganizationSection" scope="request"/>--%>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Редактирование резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="header"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" required></dd>
        </dl>
        <h3>Контакты:</h3>
        <p>
            <c:forEach var="type" items="<%=ContactType.values()%>">
        <dl>
            <dt>${type.title}</dt>
            <c:set var="contactValue" value="${resume.getContact(type)}"/>
            <dd><input type="text" name="${type.name()}" size=30 value="${contactValue}"></dd>
        </dl>
        </c:forEach>
        <p>
        <h3>Секции:</h3>
        <p>
            <c:forEach var="sectionType" items="<%=SectionType.values()%>">

            <c:if test="${sectionType!=SectionType.EDUCATION && sectionType!=SectionType.EXPERIENCE}">
            <label>${sectionType.title}</label><br/>
                <c:set var="sectionValue" value="${resume.getSection(sectionType.name())}"/>
            <textarea id="my-textarea" cols="40" rows="4"
                      name="${sectionType.name()}">${sectionValue}</textarea><br/>
            </c:if>

<%--            <c:if test="${sectionType==SectionType.EXPERIENCE || sectionType==SectionType.EDUCATION}">--%>
<%--            <c:if test="${sectionType==SectionType.EXPERIENCE}">--%>
<%--                <c:set var="allOrganizations" value="${experience.allOrganizations}"/>--%>
<%--            </c:if>--%>
<%--            <c:if test="${sectionType==SectionType.EDUCATION}">--%>
<%--                <c:set var="allOrganizations" value="${education.allOrganizations}"/>--%>
<%--            </c:if>--%>

<%--            <label>${sectionType.title}</label><br/>--%>
<%--            <c:forEach var="organization" items="${allOrganizations}" varStatus="status_org">--%>
<%--            <input type="hidden" name="organizationCount" value="${status_org.count}">--%>
<%--        <hr>--%>
<%--        <dl>--%>
<%--            <dt>Организация</dt>--%>
<%--            <dd><input type="text" name="organization.name-${status_org.index}" size=80 value="${organization.name}">--%>
<%--            </dd>--%>
<%--        </dl>--%>
<%--        <dl>--%>
<%--            <dt>Сайт</dt>--%>
<%--            <dd><input type="text" name="organization.website-${status_org.index}" size=80--%>
<%--                       value="${organization.website}"></dd>--%>
<%--        </dl>--%>
<%--        <c:forEach var="period" items="${organization.periods}" varStatus="status_period">--%>
<%--            <input type="hidden" name="periodCount${status_org.index}" value="${status_period.count}">--%>
<%--            <dl>--%>
<%--                <dt>Дата начала</dt>--%>
<%--                <dd><input type="date" name="period.startDate-${status_org.index}${status_period.index}" size=80--%>
<%--                           value="${period.startDate}"></dd>--%>
<%--            </dl>--%>
<%--            <dl>--%>
<%--                <dt>Дата окончания</dt>--%>
<%--                <dd><input type="date" name="period.endDate-${status_org.index}${status_period.index}" size=80--%>
<%--                           value="${period.endDate}"></dd>--%>
<%--            </dl>--%>
<%--            <dl>--%>
<%--                <dt>Должность</dt>--%>
<%--                <dd><input type="text" name="period.title-${status_org.index}${status_period.index}" size=80--%>
<%--                           value="${period.title}"></dd>--%>
<%--            </dl>--%>
<%--            <label>Описание</label><br/>--%>
<%--            <textarea id="my-textarea" cols="40" rows="4"--%>
<%--                      name="period.description-${status_org.index}${status_period.index}">${period.description}</textarea><br/>--%>
<%--        </c:forEach>--%>
<%--        <hr>--%>
<%--        </c:forEach>--%>
<%--        </c:if>--%>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
</body>
</html>