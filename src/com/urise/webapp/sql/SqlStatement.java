package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlStatement<T> {
    T run (PreparedStatement ps) throws SQLException;
}
