<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page import="com.urise.webapp.util.DataUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>

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
            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}" required
                       onblur="checkSpaces(this)"></dd>
        </dl>
        <h3>Контакты:</h3>
        <p>
            <c:forEach var="type" items="<%=ContactType.values()%>">
        <dl>
            <dt>${type.title}</dt>
            <c:set var="contactValue" value="${resume.getContact(type)}"/>
            <dd><input type="text" name="${type.name()}" size=30 value="${contactValue}">
            </dd>
        </dl>
        </c:forEach>
        <p>
        <h3>Секции:</h3>
        <p>
            <c:forEach var="sectionType" items="<%=SectionType.values()%>">
                <c:set var="sectionValue" value="${resume.getSection(sectionType)}"/>
                <jsp:useBean id="sectionValue" type="com.urise.webapp.model.AbstractSection"/>

            <c:if test="${sectionType!=SectionType.EDUCATION && sectionType!=SectionType.EXPERIENCE}">
            <label>${sectionType.title}</label><br/>

            <textarea id="my-textarea" cols="40" rows="4"
                      name="${sectionType.name()}">${sectionValue}</textarea><br/>
            </c:if>

            <c:if test="${sectionType==SectionType.EXPERIENCE || sectionType==SectionType.EDUCATION}">
            <label>${sectionType.title}</label><br/>

            <c:forEach var="organization" items="<%=((OrganizationSection)sectionValue).getAllOrganizations()%>"
                       varStatus="status_org">
        <hr>
        <dl>
            <dt>Организация</dt>
            <dd><input type="text" name="${sectionType.name()}organizationName" size=80
                       value="${organization.name}"
                       varStatus="status_org">
            </dd>
        </dl>
        <dl>
            <dt>Сайт</dt>
            <dd><input type="text" name="${sectionType.name()}website" size=80
                       value="${organization.website}"></dd>
        </dl>
        <c:forEach var="period" items="${organization.periods}">
            <jsp:useBean id="period" type="com.urise.webapp.model.Period"/>
            <input type="hidden" name="${sectionType.name()}${status_org.index}" value="${organization.periods}">
            <dl>
                <dt>Дата начала</dt>
                <dd><input type="date" name="${sectionType.name()}${status_org.index}startDate"
                           size=80
                           value="<%=DataUtil.formatToJsp(period.getStartDate())%>"></dd>
            </dl>
            <dl>
                <dt>Дата окончания</dt>
                <dd><input type="date" name="${sectionType.name()}${status_org.index}endDate"
                           size=80
                           value="<%=DataUtil.formatToJsp(period.getEndDate())%>"></dd>
            </dl>
            <dl>
                <dt>Должность</dt>
                <dd><input type="text" name="${sectionType.name()}${status_org.index}title"
                           size=80
                           value="${period.title}"></dd>
            </dl>
            <label>Описание</label><br/>
            <textarea id="my-textarea" cols="40" rows="4"
                      name="${sectionType.name()}${status_org.index}description">
                    ${period.description}
            </textarea><br/>
        </c:forEach>
        <hr>
        </c:forEach>
        </c:if>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<script>
    function checkSpaces(input) {
        const value = input.value;
        if (value.trim() === '') {
            alert('ФИО не может быть пустым');
        }
    }
</script>
</body>
</html>