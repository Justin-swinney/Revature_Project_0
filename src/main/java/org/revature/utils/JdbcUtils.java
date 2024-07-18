package org.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {
    // Database Connection Properties stored in environmental variables
    private static final String url = System.getenv("DB_URL");
    private static final String userName = System.getenv("DB_USERNAME");
    private static final String password = System.getenv("DB_PASSWORD");

    // Database Connection
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver"); //searching for the postgres driver, which we have as a dependency
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage()); //This tells in the console us what went wrong
            System.out.println("problem occurred locating driver");
        }
        return DriverManager.getConnection(url, userName, password);
    }
}
