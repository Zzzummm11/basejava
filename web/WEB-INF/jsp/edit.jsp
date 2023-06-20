<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page import="com.urise.webapp.util.DataUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/theme/${theme}.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/edit-resume-styles.css">
    <title>Редактирование резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="header"/>
<form method="post" action="resume" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="uuid" value="${resume.uuid}">
    <input type="hidden" name="theme" value="${theme}">
    <div class="scrollable-panel">
        <div class="form-wrapper">

            <div class="section">ФИО</div>
            <input type="text" name="fullName" size="50" value="${resume.fullName}" required
                   onblur="checkSpaces(this)">

            <div class="section">Контакты</div>

            <c:forEach var="type" items="<%=ContactType.values()%>">

                <c:set var="contactValue" value="${resume.getContact(type)}"/>
                <input class="field" type="text" name="${type.name()}" size=30 placeholder="${type.title}"
                       value="${contactValue}">
            </c:forEach>

            <div class="spacer"></div>

            <div class="section">Секции</div>

            <c:forEach var="sectionType" items="<%=SectionType.values()%>">
                <c:set var="sectionValue" value="${resume.getSection(sectionType)}"/>
                <jsp:useBean id="sectionValue" type="com.urise.webapp.model.AbstractSection"/>

                <c:if test="${sectionType!=SectionType.EDUCATION && sectionType!=SectionType.EXPERIENCE}">
                    <div class="field-label">${sectionType.title}</div>
                    <textarea class="field" name="${sectionType.name()}">${sectionValue}</textarea>
                </c:if>

                <c:if test="${sectionType==SectionType.EXPERIENCE || sectionType==SectionType.EDUCATION}">
                    <div class="field-label">${sectionType.title}</div>

                    <c:forEach var="organization"
                               items="<%=((OrganizationSection)sectionValue).getAllOrganizations()%>"
                               varStatus="status_org">

                        <input class="field" type="text" placeholder="Название"
                               name="${sectionType.name()}" size=100 value="${organization.name}"
                               varStatus="status_org">

                        <input class="field" type="text" placeholder="Ссылка"
                               name="${sectionType.name()}website" size=100 value="${organization.website}">

                        <c:forEach var="period" items="${organization.periods}">
                            <jsp:useBean id="period" type="com.urise.webapp.model.Period"/>
                            <input type="hidden" name="${sectionType.name()}${status_org.index}"
                                   value="${organization.periods}">

                            <div class="date-section">

                                <input class="field date"
                                       type="date" name="${sectionType.name()}${status_org.index}startDate"
                                       size=80
                                       value="<%=DataUtil.formatToJsp(period.getStartDate())%>">

                                <input class="field date"
                                       type="date" name="${sectionType.name()}${status_org.index}endDate"
                                       size=80
                                       value="<%=DataUtil.formatToJsp(period.getEndDate())%>">
                            </div>

                            <input class="field"
                                   type="text" placeholder="Заголовок"
                                   name="${sectionType.name()}${status_org.index}title"
                                   size=80
                                   value="${period.title}">

                            <textarea class="field" placeholder="Описание"
                                      name="${sectionType.name()}${status_org.index}description">${period.description}</textarea><br/>
                        </c:forEach>
                    </c:forEach>
                </c:if>
            </c:forEach>
            <div class="spacer"></div>
            <div class="button-section">
                <button class="red-cancel-button" type="button" onclick="window.history.back()">Отменить</button>
                <button class="green-submit-button" type="submit">Сохранить</button>
            </div>
        </div>
    </div>
</form>
<jsp:include page="footer"/>
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