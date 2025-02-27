
package com.example.backend.DAO;

import com.example.backend.DB.DBConnect;
import com.example.backend.models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    Connection connection = DBConnect.getInstance().getConnection();
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("fullName"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setDob(rs.getString("dob"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean addUser(User user) {
        String query = "INSERT INTO users (username, password, email, fullName, phone, dob, address, role, status, created_at, updated_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP())";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            // Mã hóa mật khẩu
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            // Thiết lập các giá trị cho PreparedStatement
            ps.setString(1, user.getUsername());  // Tên tài khoản
            ps.setString(2, hashedPassword);  // Mật khẩu đã mã hóa
            ps.setString(3, user.getEmail());  // Email
            ps.setString(4, user.getFullName());  // Họ và tên
            ps.setString(5, user.getPhone());  // Số điện thoại
            ps.setString(6, user.getDob());  // Ngày sinh (kiểu String hoặc Date tùy vào cách bạn xử lý)
            ps.setString(7, user.getAddress());  // Địa chỉ
            ps.setString(8, user.getRole() != null ? user.getRole() : "user");  // Vai trò (mặc định là "user")
            ps.setString(9, user.getStatus() != null ? user.getStatus() : "Active");  // Trạng thái (mặc định là "Active")

            // Thực thi truy vấn
            int rowsAffected = ps.executeUpdate();

            // Kiểm tra xem có thêm thành công không
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;  // Nếu xóa thành công, số dòng bị ảnh hưởng > 0
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Nếu có lỗi, trả về false
        }
    }
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("fullName"),
                        resultSet.getString("phone"),
                        resultSet.getString("role"),
                        resultSet.getString("status"),
                        resultSet.getString("dob"),
                        resultSet.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean updatePassword(int userId, String hashedPassword) {
        String query = "UPDATE users SET password = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, hashedPassword);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET fullName = ?, username = ?, email = ?, phone = ?, dob = ?, address = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getDob());
            stmt.setString(6, user.getAddress());
            stmt.setInt(7, user.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Nếu có lỗi, trả về false
        }
    }
    public User getUserByEmail(String email) {
        try {
            String sql = "SELECT * FROM users WHERE email = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new User(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updatePassword(String email, String newPassword) {
        try {
            String sql = "UPDATE users SET password = ? WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            statement.setString(1, hashedPassword);
            statement.setString(2, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean register(User user) {
        String query = "INSERT INTO users (username, password, email, fullName, phone, role, status, dob, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword()); // Hash the password in real-world apps
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFullName());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getRole());
            ps.setString(7, user.getStatus());
            ps.setDate(8, user.getDob() != null ? Date.valueOf(user.getDob()) : null);
            ps.setString(9, user.getAddress());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Login user
    public User login(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password"); // Lấy mật khẩu đã mã hóa từ DB
                    // Kiểm tra mật khẩu người dùng nhập với mật khẩu đã mã hóa
                    if (BCrypt.checkpw(password, hashedPassword)) {
                        return new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("email"),
                                rs.getString("fullName"),
                                rs.getString("phone"),
                                rs.getString("role"),
                                rs.getString("status"),
                                rs.getString("dob"),
                                rs.getString("address")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy hoặc đăng nhập thất bại
    }

}
