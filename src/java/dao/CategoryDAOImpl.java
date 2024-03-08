/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Category;
import model.Product;

/**
 *
 * @author admin
 */
public class CategoryDAOImpl extends DBContext implements CategoryDAO {

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        try {

            Connection conn = getConnection();
            // Hash the password using SHA-256
            String sql = "select * from categories";
            try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    int categoryId = rs.getInt("id");
                    String name = rs.getString("name");
                    
                    Category category = new Category(categoryId, name);
                    
                    categories.add(category);

                }
            } catch (Exception e) {

            }

        } catch (Exception e) {

        }
        return categories;
    }

}
