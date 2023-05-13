package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String uuid = request.getParameter("uuid");
        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("<head>");
        html.append("<meta charset=\"UTF-8\">");
        html.append("<link rel=\"stylesheet\" href=\"css/style.css\">");

        if (uuid != null) {
            html.append("<title>Выбранное резюме</title>");
            html.append("</head>");
            html.append("<body>");
            html.append("<table>");
            html.append("<caption>Выбранное резюме</caption>");
            html.append("<tr><th>uuid</th><th>fullName</th></tr>");
            html.append("<tr>");
            Resume r = Config.get().getStorage().get(uuid);
            html.append("<td>").append(uuid).append("</td>");
            html.append("<td>").append(r.getFullName()).append("</td>");
            html.append("</tr>");
            html.append("</table>");
            html.append("<a href=\"http://localhost:8080/resumes/resume\" class=\"link-style\">Назад</a>");
        } else {
            html.append("<title>Таблица резюме</title>");
            html.append("</head>");
            html.append("<body>");
            html.append("<table>");
            html.append("<caption>Список всех резюме</caption>");
            html.append("<tr><th>№</th><th>uuid</th><th>fullName</th></tr>");
            html.append("<tr>");
            List<Resume> list = Config.get().getStorage().getAllSorted();
            for (int i = 0; i < list.size(); i++) {
                html.append("<td>").append(i + 1).append("</td>");
                html.append("<td><a href=\"resume?uuid=");
                html.append(list.get(i).getUuid());
                html.append("\"title=\"Посмотреть отдельно\"");
                html.append("id=\"example-link\">");
                html.append(list.get(i).getUuid()).append("</a></td>");
                html.append("<td>").append(list.get(i).getFullName()).append("</td></tr>");
            }
            html.append("</tr>");
            html.append("</table>");
            html.append("<a href=\"http://localhost:8080/resumes\" class=\"link-style\">На главную</a>");
        }
        html.append("</body>");
        html.append("</html>");
        PrintWriter pw = response.getWriter();
        pw.println(html);
    }
}
