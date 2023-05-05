package com.urise.webapp.model.storage;

import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dburl, String dbuser, String dbpassword) {
        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dburl, dbuser, dbpassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }


    @Override
    public void save(Resume r) {
        sqlHelper.transactionExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    saveContacts(r, conn);
                    return null;
                }
        );
    }

    private void saveContacts(final Resume r, final Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @Override
    public Resume get(final String uuid) {
        return sqlHelper.execute("" +
                        "   SELECT * FROM resume r " +
                        "LEFT JOIN contact c" +
                        "       ON r.uuid = c.resume_uuid" +
                        "    WHERE r.uuid=?",
                (ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        String value = rs.getString("value");
                        ContactType type = ContactType.valueOf(rs.getString("type"));
                        r.addContact(type, value);
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
                        saveContacts(r, conn);
                    }
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
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name")) {
                Map<String, Resume> mapOfResumes = new LinkedHashMap<>();
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    String fullName = rs.getString("full_name");
                    mapOfResumes.put(uuid, new Resume(uuid, fullName));
                }
                try (PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM contact")) {
                    ResultSet rs1 = ps1.executeQuery();
                    while (rs1.next()) {
                        String resume_uuid = rs1.getString("resume_uuid");
                        String value = rs1.getString("value");
                        ContactType type = ContactType.valueOf(rs1.getString("type"));
                        mapOfResumes.get(resume_uuid).addContact(type, value);
                    }
                }
                return mapOfResumes.values().stream().toList();
            }
        });
    }
}



