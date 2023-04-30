package com.urise.webapp.model.storage;

import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dburl, String dbuser, String dbpassword) {
        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dburl, dbuser, dbpassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void save(final Resume r) {
        sqlHelper.runSqlRequest("INSERT INTO resume (uuid,full_name) VALUES(?,?)", (ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
            return null;
        }));
    }

    @Override
    public Resume get(final String uuid) {
        return sqlHelper.runSqlRequest("SELECT * FROM resume r WHERE r.uuid=?", (ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        }));
    }

    @Override
    public void update(final Resume r) {
        sqlHelper.runSqlRequest("UPDATE resume r SET full_name=? WHERE r.uuid=?", (ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        }));
    }

    @Override
    public void delete(final String uuid) {
        sqlHelper.runSqlRequest("DELETE FROM resume r WHERE r.uuid=?", (ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        }));
    }

    @Override
    public int size() {
        return sqlHelper.runSqlRequest("SELECT COUNT(*) AS count FROM resume", (ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }));
    }

    @Override
    public void clear() {
        sqlHelper.runSqlRequest("DELETE FROM resume", (ps -> {
            ps.executeUpdate();
            return null;
        }));
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.runSqlRequest("SELECT * FROM resume ORDER BY full_name", (ps -> {
            List<Resume> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String fullName = rs.getString("full_name");
                list.add(new Resume(uuid, fullName));
            }
            return list;
        }));
    }
}
