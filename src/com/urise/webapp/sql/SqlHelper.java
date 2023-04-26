package com.urise.webapp.sql;

import com.urise.webapp.exeption.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void runSqlRequest(String sqlRequest, SqlStatement statement) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlRequest)) {
            statement.run(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}
