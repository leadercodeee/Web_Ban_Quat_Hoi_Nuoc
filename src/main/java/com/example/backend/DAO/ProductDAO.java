
package com.example.backend.DAO;

import com.example.backend.DB.DBConnect;
import com.example.backend.models.Product;
import com.example.backend.models.ProductImage;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDAO {
    ProductImageDAO productImageDAO = new ProductImageDAO();
    Connection connection = DBConnect.getInstance().getConnection();
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategoryId(rs.getInt("category_id"));
                product.setPrice(rs.getDouble("price"));
                product.setBrand(rs.getString("brand"));
                product.setStock(rs.getInt("stock"));
                product.setDescription(rs.getString("description"));
                product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                product.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                List<ProductImage> images = productImageDAO.getImagesByProductId(product.getId());
                product.setImages(images);
                productList.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public Product getProductById(int productId) {
        Product product = null;
        String query = "SELECT * FROM products WHERE id = ?";

        try (
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("category_id"),
                        rs.getDouble("price"),
                        rs.getString("brand"),
                        rs.getInt("stock"),
                        rs.getString("description"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
                List<ProductImage> images = productImageDAO.getImagesByProductId(product.getId());
                product.setImages(images);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public int addProduct(Product product) {
        String sql = "INSERT INTO products (name, category_id, price, brand, stock, description) VALUES (?, ?, ?, ?, ?, ?)";
        try (
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getCategoryId());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getBrand());
            stmt.setInt(5, product.getStock());
            stmt.setString(6, product.getDescription());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Trả về ID sản phẩm vừa thêm
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Thêm thất bại
    }

    public boolean deleteProductById(int productId) {
        String query = "DELETE FROM products WHERE id = ?";

        try (
                PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, productId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Product> getTop3Products() throws SQLException {
        String sql = "SELECT p.id, p.name, p.description, p.price, p.stock, p.brand, p.category_id, p.created_at, p.updated_at " +
                "FROM products p " +
                "ORDER BY p.created_at DESC LIMIT 3";

        List<Product> products = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                String brand = rs.getString("brand");
                int categoryId = rs.getInt("category_id");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();

                // Tạo đối tượng Product
                Product product = new Product(productId, name, categoryId, price, brand, stock, description, createdAt, updatedAt);

                // Lấy hình ảnh sản phẩm
                List<ProductImage> images = getProductImages(productId);
                product.setImages(images);

                products.add(product);
            }
        }
        return products;
    }

    private List<ProductImage> getProductImages(int productId) throws SQLException {
        String sql = "SELECT id, image_url, is_primary,created_at,updated_at FROM product_images WHERE product_id = ?";
        List<ProductImage> images = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int imageId = rs.getInt("id");
                String imageUrl = rs.getString("image_url");
                boolean isPrimary = rs.getBoolean("is_primary");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
                images.add(new ProductImage(imageId, productId, imageUrl, isPrimary, createdAt, updatedAt));
            }
        }
        return images;
    }
    public List<Product> getProductsByCategoryId(int categoryId) {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM products WHERE category_id = ?"; // Giả sử bạn có trường category_id trong bảng products

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, categoryId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                String brand = rs.getString("brand");
                int cateId = rs.getInt("category_id");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
                Product product = new Product(productId, name, cateId, price, brand, stock, description, createdAt, updatedAt);
                List<ProductImage> images = productImageDAO.getImagesByProductId(product.getId());
                product.setImages(images);
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
    public List<Product> get5ProductsByCategoryId(int categoryId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category_id = ? LIMIT 5"; // Giả sử bạn có trường category_id trong bảng products
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, categoryId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                String brand = rs.getString("brand");
                int cateId = rs.getInt("category_id");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
                Product product = new Product(productId, name, cateId, price, brand, stock, description, createdAt, updatedAt);
                List<ProductImage> images = productImageDAO.getImagesByProductId(product.getId());
                product.setImages(images);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<>();
        String sql = """
        SELECT p.id, p.name, p.category_id, p.price, p.brand, p.stock, p.description, 
               p.created_at, p.updated_at, 
               pi.image_url AS image_url 
        FROM products p 
        LEFT JOIN product_images pi ON p.id = pi.product_id 
        WHERE p.name LIKE ? OR p.description LIKE ? OR p.brand LIKE ?
        ORDER BY p.updated_at DESC
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String queryKeyword = "%" + keyword + "%";
            stmt.setString(1, queryKeyword);
            stmt.setString(2, queryKeyword);
            stmt.setString(3, queryKeyword);

            ResultSet rs = stmt.executeQuery();

            Map<Integer, Product> productMap = new HashMap<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                Product product = productMap.get(id);

                if (product == null) {
                    product = new Product(
                            id,
                            rs.getString("name"),
                            rs.getInt("category_id"),
                            rs.getDouble("price"),
                            rs.getString("brand"),
                            rs.getInt("stock"),
                            rs.getString("description"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getTimestamp("updated_at").toLocalDateTime()
                    );
                    productMap.put(id, product);
                }

                String imageUrl = rs.getString("image_url");
                if (imageUrl != null) {
                    if (product.getImages() == null) {
                        product.setImages(new ArrayList<>());
                    }
                    product.getImages().add(new ProductImage(imageUrl));
                }
            }
            products.addAll(productMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
