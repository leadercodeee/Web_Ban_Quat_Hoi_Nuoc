
package com.example.backend.services;
import com.example.backend.DAO.CategoryDAO;
import com.example.backend.models.Category;

import java.util.List;


public class CategoryService {
    private static CategoryService instance;
    private static final int DEFAULT_ID = 0;

    private CategoryService() {}

    public static CategoryService getInstance() {
        if (instance == null) {
            instance = new CategoryService();
        }
        return instance;
    }

    // Phương thức thêm danh mục
    public boolean addCategory(String name, String description) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Category name cannot be empty.");
            return false;
        }

        Category category = new Category(DEFAULT_ID, name, description, null, null);
        return CategoryDAO.getInstance().addCategory(category);
    }

    // Phương thức lấy danh mục theo ID
    public Category getCategoryById(int id) {
        return CategoryDAO.getInstance().getCategoryById(id);
    }

    public List<Category> getAllCategories() {
        return CategoryDAO.getInstance().getAllCategories();
    }
    public boolean deleteCategory(int id) {
        return CategoryDAO.getInstance().deleteCategory(id);
    }

    public boolean updateCategory(Category category) {
        return CategoryDAO.getInstance().updateCategory(category);
    }


}
