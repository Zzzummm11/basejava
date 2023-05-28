<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="header"/>
    <table>
        <tr>
            <th>ФИО</th>
            <th>Почта</th>
            <th>Редактировать</th>
            <th>Удалить</th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view"
                       title="Посмотреть отдельно" id="example-link">${resume.fullName}</a></td>
                <td><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%>
                </td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit" id="example-link">Редактировать</a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete" id="example-link">Удалить</a></td>
            </tr>
        </c:forEach>
    </table>
    <button type="submit" onclick="window.location.href = 'resume?action=add'">Добавить резюме</button>
</body>
</html>
