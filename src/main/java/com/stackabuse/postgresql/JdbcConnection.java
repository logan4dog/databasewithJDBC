package com.stackabuse.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcConnection {
    private static final Logger LOGGER =
            Logger.getLogger(JdbcConnection.class.getName());
        private static Optional<Connection> connection = Optional.empty();

        public static Optional<Connection> getConnection() {
            if (connection.isPresent()) {
                String url = "jdbc:postgresql://localhost:5434/databasename";
                String user = "user";
                String password = "password";

                try {
                    connection = Optional.ofNullable(
                        DriverManager.getConnection(url, "user", "password"));
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                    System.out.format("Problem in DAO %s", "connection");
                }
            }

            return connection;
        }
}
        
