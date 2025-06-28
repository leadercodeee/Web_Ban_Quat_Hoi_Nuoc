package com.example.backend.DB;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBConnect {

    static String url = "jdbc:mysql://localhost:3306/backend";
    static String user = "root";
    static String pass = "";
    static Connection connection;
    static
    DBConnect instance;


    private static DBConnect instance;
    private HikariDataSource dataSource;

    private DBConnect() {
        try {
            // Load driver explicit
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy MySQL Driver", e);
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/backend");
        config.setUsername("root");
        config.setPassword("");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        // Giữ lại cài đặt mới cho pool size
        config.setMaximumPoolSize(30);

        dataSource = new HikariDataSource(config);
    }

    public static synchronized DBConnect getInstance() {
        if (instance == null) {
            instance = new DBConnect();
        }
        return instance;
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user,pass);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Kết nối đã được đóng.");
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        try (Connection connection = DBConnect.getInstance().getConnection()) {
            System.out.println("Kết nối thành công!");


    public Connection getConnection() {
        try {
            return dataSource.getConnection();

        } catch (SQLException e) {
            throw new RuntimeException("Không thể lấy kết nối", e);
        }
    }

    public void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    public static void main(String[] args) {
        try (Connection conn = DBConnect.getInstance().getConnection()) {
            System.out.println("Kết nối thành công!");
        } catch (RuntimeException | SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.getInstance().closeDataSource();
        }
    }
}