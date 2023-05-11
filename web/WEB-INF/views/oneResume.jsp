<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="com.urise.webapp.model.Resume" %>
<%@ page import="com.urise.webapp.Config" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Выбранное резюме</title>
</head>
<body>
<table>
    <caption>Выбранное резюме</caption>
    <tr>

        <th>uuid</th>
        <th>fullName</th>
    </tr>
    <tr>
        <%
            String uuid = request.getParameter("uuid");
            Resume r = Config.get().getStorage().get(uuid);
            out.println("<td>" + uuid + "</td>");
            out.println("<td>" + r.getFullName() + "</td>");
        %>
    </tr>
</table>
<a href="http://localhost:8080/resumes/resume" class="link-style">Назад</a>
</body>
</html>
