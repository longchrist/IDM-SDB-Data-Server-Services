package com.idm.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static HikariConfig dbConfig;
    private static HikariConfig hikariConfig = new HikariConfig("/hikari.properties");

    private static HikariDataSource ds = new HikariDataSource(hikariConfig);

    public static <T> T execute(ConnectionCallback<T> callback) {
        try (Connection conn = ds.getConnection()) {
            return callback.doInConnection(conn);
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    public static interface ConnectionCallback<T> {
        public T doInConnection(Connection conn) throws SQLException;
    }
}
