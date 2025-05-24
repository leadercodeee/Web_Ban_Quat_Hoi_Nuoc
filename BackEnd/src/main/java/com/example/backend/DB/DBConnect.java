
package com.example.backend.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    static String url = "jdbc:mysql://localhost:3306/backend";
    static String user = "root";
    static String pass = "";
    static Connection connection;
    static
    DBConnect instance;

    public static DBConnect getInstance() {
        if (instance == null) instance = new DBConnect();
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
