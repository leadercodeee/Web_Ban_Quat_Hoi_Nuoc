package com.example.backend.services;

import com.example.backend.DAO.DiscountDAO;
import com.example.backend.DB.DBConnect;
import com.example.backend.models.Discount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DiscountService {
    private DiscountDAO discountDAO = new DiscountDAO();
    public List<Discount> getAllDiscounts(){
        return discountDAO.getAllDiscounts();
    }
    public void addDiscount(Discount discount) {
        discountDAO.insertDiscount(discount);
    }
    public void deleteDiscount(int discountId) {
        discountDAO.delete(discountId);
    }
    public Discount getDiscountById(int discountId) {
        Discount discount = null;
        String query = "SELECT * FROM discounts WHERE id = ?";
        try (Connection connection = DBConnect.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, discountId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                discount = new Discount();
                discount.setId(resultSet.getInt("id"));
                discount.setName(resultSet.getString("name"));
                discount.setDiscountValue(resultSet.getBigDecimal("discount_value"));
                discount.setStartDate(resultSet.getString("start_date"));
                discount.setEndDate(resultSet.getString("end_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discount;
    }
    public void update(Discount discount){
        discountDAO.update(discount);
    }
}