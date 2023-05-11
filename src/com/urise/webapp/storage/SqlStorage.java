package com.urise.webapp.storage;

import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dburl, String dbuser, String dbpassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
        throw new RuntimeException("Postgres JDBC Driver is not find classpath", e);
    }
        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dburl, dbuser, dbpassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void save(final Resume r) {
        sqlHelper.transactionExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    saveContacts(r, conn);
                    saveSections(r, conn);
                    return null;
                }
        );
    }

    @Override
    public Resume get(final String uuid) {
        return sqlHelper.execute("" +
                        "   SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "       ON r.uuid = c.resume_uuid " +
                        "LEFT JOIN section s " +
                        "       ON r.uuid = s.resume_uuid " +
                        "    WHERE r.uuid=?",
                (ps -> {
                    ps.setString(1, uuid);

                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));

                    do {
                        addContactToResume(rs, r);
                        addSectionToResume(rs, r);
                    } while (rs.next());

                    return r;
                }));
    }

    @Override
    public void update(final Resume r) {
        sqlHelper.transactionExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume r SET full_name=? WHERE r.uuid=?")) {
                        ps.setString(1, r.getFullName());
                        ps.setString(2, r.getUuid());
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(r.getUuid());
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact c WHERE c.resume_uuid = ?")) {
                        ps.setString(1, r.getUuid());
                        ps.executeUpdate();
                    }
                    saveContacts(r, conn);

                    try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section s WHERE s.resume_uuid = ?")) {
                        ps.setString(1, r.getUuid());
                        ps.executeUpdate();
                    }
                    saveSections(r, conn);

                    return null;
                }
        );
    }

    @Override
    public void delete(final String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid=?", (ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        }));
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", (ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", (ps -> {
            ps.executeUpdate();
            return null;
        }));
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionExecute(conn -> {
            final Map<String, Resume> mapOfResumes = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    String fullName = rs.getString("full_name");
                    mapOfResumes.put(uuid, new Resume(uuid, fullName));
                }
            }
            try (PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    addContactToResume(rs1, mapOfResumes.get(rs1.getString("resume_uuid")));
                }
            }
            try (PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM section")) {
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    addSectionToResume(rs1, mapOfResumes.get(rs1.getString("resume_uuid")));
                }
            }
            return new ArrayList<>(mapOfResumes.values());
        });
    }


    private void saveContacts(final Resume r, final Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, contact_type, contact_value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void saveSections(final Resume r, final Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, section_type, section_value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> s : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, s.getKey().name());
                String value = writeSectionToSQL(s.getKey(), s.getValue());
                ps.setString(3, value);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private String writeSectionToSQL(final SectionType sectionType, final AbstractSection section) {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:

                return String.valueOf(section);

            case ACHIEVEMENT:
            case QUALIFICATION:

                List<String> list = ((ListTextSection) section).getList();
                StringBuilder sb = new StringBuilder(list.get(0));

                for (int i = 1; i < list.size(); i++) {
                    sb.append("\n").append(list.get(i));
                }

                return String.valueOf(sb);

//            case EXPERIENCE:
//            case EDUCATION:

            default:
                return null;

        }
    }

    private void addContactToResume(final ResultSet rs, final Resume r) throws SQLException {
        String value = rs.getString("contact_value");
        ContactType type = ContactType.valueOf(rs.getString("contact_type"));
        r.addContact(type, value);
    }

    private void addSectionToResume(final ResultSet rs, final Resume r) throws SQLException {
        SectionType sectionType = SectionType.valueOf(rs.getString("section_type"));
        AbstractSection abstractSection = readSectionFromSQL(rs, sectionType);
        r.addSection(sectionType, abstractSection);
    }

    private AbstractSection readSectionFromSQL(final ResultSet rs, final SectionType sectionType) throws SQLException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(rs.getString("section_value"));

            case ACHIEVEMENT:
            case QUALIFICATION:
                String value = rs.getString("section_value");
                return new ListTextSection(Arrays.asList(value.split("\n")));

//            case EXPERIENCE:
//            case EDUCATION:

            default:
                return null;
        }
    }

}



