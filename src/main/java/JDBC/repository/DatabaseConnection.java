package JDBC.repository;

import JDBC.util.PropertiesLoader;
import lombok.SneakyThrows;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection implements Closeable {

    private static final String URL = PropertiesLoader.getProperty("url");
    private static final String USERNAME = PropertiesLoader.getProperty("username");
    private static final String PASSWORD = PropertiesLoader.getProperty("password");
    private static final String JDBC_DRIVER = PropertiesLoader.getProperty("driver");

    private static DatabaseConnection databaseConnection;
    private Connection connection;

    @SneakyThrows
    private DatabaseConnection() {

        Class.forName(JDBC_DRIVER);
        this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    @SneakyThrows
    public static DatabaseConnection getInstance() {

        if (databaseConnection == null || databaseConnection.getConnection().isClosed()) {
            databaseConnection = new DatabaseConnection();
        }

        return databaseConnection;
    }

    public Connection getConnection() {

        return connection;
    }

    @SneakyThrows
    @Override
    public void close() throws IOException {
        connection.close();
    }
}