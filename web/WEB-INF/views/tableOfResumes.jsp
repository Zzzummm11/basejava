<%@ page contentType="text/html;charset=UTF-8" %>

<%@ page import="com.urise.webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="com.urise.webapp.Config" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Таблица резюме</title>
</head>
<body>
<table>
    <caption>Список всех резюме</caption>
    <tr>
        <th>№</th>
        <th>uuid</th>
        <th>fullName</th>
    </tr>
    <%
        List<Resume> list = Config.get().getStorage().getAllSorted();

        for (int i = 0; i < list.size(); i++) {
            out.println("<tr><td>" + (i + 1) + "</td>");
            out.println("<td><a href=\"resume?uuid=" + list.get(i).getUuid()
                     + "\"title=\"Посмотреть отдельно\"" + "id=\"example-link\"" + "\">"
                    + list.get(i).getUuid() + "</a></td>");
            out.println("<td>" + list.get(i).getFullName() + "</td> </tr>");
        };
    %>
</table>
    <a href="http://localhost:8080/resumes" class="link-style">На главную</a>
</body>
</html>
