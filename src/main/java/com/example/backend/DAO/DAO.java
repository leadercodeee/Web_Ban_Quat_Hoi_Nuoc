
package com.example.backend.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAO {
    Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private static DAO instance = null;
    private DAO(){

    }
    public static DAO getInstance() {
        if (instance == null) instance = new DAO();
        return instance;
    }
}
