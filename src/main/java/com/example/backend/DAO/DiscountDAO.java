
package com.example.backend.DAO;

import com.example.backend.DB.DBConnect;
import com.example.backend.models.Discount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountDAO {
    Connection connection = DBConnect.getInstance().getConnection();
    public List<Discount> getAllDiscounts() {
        List<Discount> discounts = new ArrayList<>();
        String sql = "SELECT id, name, discount_value, start_date, end_date FROM discounts";

        try (
                PreparedStatement pst = connection.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Discount discount = new Discount();
                discount.setId(rs.getInt("id"));
                discount.setName(rs.getString("name"));
                discount.setDiscountValue(rs.getBigDecimal("discount_value"));
                discount.setStartDate(rs.getTimestamp("start_date").toString());
                discount.setEndDate(rs.getTimestamp("end_date").toString());
                discounts.add(discount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discounts;
    }
    public void insertDiscount(Discount discount) {
        String query = "INSERT INTO discounts (name, discount_value, start_date, end_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, discount.getName());
            statement.setBigDecimal(2, discount.getDiscountValue());
            statement.setString(3, discount.getStartDate());
            statement.setString(4, discount.getEndDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(int discountId) {
        String query = "DELETE FROM discounts WHERE id = ?";
        try (
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, discountId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(Discount discount) {
        String query = "UPDATE discounts SET name = ?, discount_value = ?, start_date = ?, end_date = ? WHERE id = ?";
        try (
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, discount.getName());
            statement.setBigDecimal(2, discount.getDiscountValue());
            statement.setString(3, discount.getStartDate());
            statement.setString(4, discount.getEndDate());
            statement.setInt(5, discount.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
