package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    // реализуйте настройку соединения с БД
    private static final String DB_URL="jdbc:mysql://localhost:3306/mydbtest";
    private static final String DB_USERNAME="root";
    private static final String DB_PASSWORD="Master1985%#";
    private static Connection connection = null;
    public Connection getConnection() {
                Driver driver;
        try {
            driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            System.out.println("Драйвер не загрузился");
        }
        try {
                connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);

        } catch (SQLException e){
            System.out.println("Соединение разорвано!");
        }
        return connection;
    }

}
