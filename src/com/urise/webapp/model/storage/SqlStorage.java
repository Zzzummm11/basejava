package com.urise.webapp.model.storage;

import com.urise.webapp.exeption.ExistStorageException;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dburl, String dbuser, String dbpassword) {
        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dburl, dbuser, dbpassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void save(final Resume r) {
        String sqlRequest = "INSERT INTO resume (uuid,full_name) VALUES(?,?)";
        sqlHelper.runSqlRequest(sqlRequest, (ps -> {
            try {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            } catch (SQLException e) {
                if (e.getSQLState().equals("23505")) {
                    throw new ExistStorageException(null);
                }
            }
            return null;
        }));
    }

    @Override
    public Resume get(final String uuid) {
        final AtomicReference<Resume> r = new AtomicReference<>();
        String sqlRequest = "SELECT * FROM resume r WHERE r.uuid=?";
        return sqlHelper.runSqlRequest(sqlRequest, (ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            r.set(new Resume(uuid, rs.getString("full_name")));
            return r.get();
        }));
    }

    @Override
    public void update(final Resume r) {
        String sqlRequest = "UPDATE resume r SET full_name=? WHERE r.uuid=?";
        sqlHelper.runSqlRequest(sqlRequest, (ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        }));
    }

    @Override
    public void delete(final String uuid) {
        String sqlRequest = "DELETE FROM resume r WHERE r.uuid=?";
        sqlHelper.runSqlRequest(sqlRequest, (ps -> {
            ps.setString(1, uuid);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        }));
    }

    @Override
    public int size() {
        final AtomicInteger count = new AtomicInteger(-1);
        String sqlRequest = "SELECT COUNT(*) AS count FROM resume";
        return sqlHelper.runSqlRequest(sqlRequest, (ps -> {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String temp = rs.getString("count");
                count.set(Integer.parseInt(temp));
            }
            return count.get();
        }));
    }

    @Override
    public void clear() {
        String sqlRequest = "DELETE FROM resume";
        sqlHelper.runSqlRequest(sqlRequest, (ps -> {
            ps.executeUpdate();
            return null;
        }));
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>();
        String sqlRequest = "SELECT * FROM resume ORDER BY full_name";
        return sqlHelper.runSqlRequest(sqlRequest, (ps -> {
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
