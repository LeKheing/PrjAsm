/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import model.Product;

public class ProductDAOImpl extends DBContext implements ProductDAO {

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {

            Connection conn = getConnection();
            // Hash the password using SHA-256
            String sql = "select * from products";
            try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    int productId = rs.getInt("id");
                    int categoryId = rs.getInt("c_id");
                    String name = rs.getString("name");
                    String image = rs.getString("image");
                    float price = rs.getFloat("price");
                    float weight = rs.getFloat("weight");
                    String origin = rs.getString("origin");
                    String quality = rs.getString("quality");
                    String description = rs.getString("description");

                    Product product = new Product(productId, categoryId, name, image, price, weight, origin, quality, description);
                    products.add(product);

                }
            } catch (Exception e) {

            }

        } catch (Exception e) {

        }
        return products;
    }

    @Override
    public Product getProduct(int id) {
        Product product = null;
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM products WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        product = new Product();
                        product.setId(rs.getInt("id"));
                        product.setC_id(rs.getInt("c_id"));
                        product.setName(rs.getString("name"));
                        product.setImage(rs.getString("image"));
                        product.setPrice(rs.getFloat("price"));
                        product.setWeight(rs.getFloat("weight"));
                        product.setOrigin(rs.getString("origin"));
                        product.setQuality(rs.getString("quality"));
                        product.setDescription(rs.getString("description"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Product> getProductsByCategory(int c_id) {
        List<Product> products = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM products WHERE c_id = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, c_id);

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int productId = rs.getInt("id");
                        int categoryId = rs.getInt("c_id");
                        String name = rs.getString("name");
                        String image = rs.getString("image");
                        float price = rs.getFloat("price");
                        float weight = rs.getFloat("weight");
                        String origin = rs.getString("origin");
                        String quality = rs.getString("quality");
                        String description = rs.getString("description");

                        Product product = new Product(productId, categoryId, name, image, price, weight, origin, quality, description);
                        products.add(product);
                    }
                }
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ khi kết nối cơ sở dữ liệu hoặc thực hiện truy vấn
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> getProductsByName(String namePro) {
        List<Product> products = new ArrayList<>();
        try {

            Connection conn = getConnection();
            // Hash the password using SHA-256
            String sql = "SELECT * \n"
                    + "FROM products \n"
                    + "WHERE name like '%" + namePro + "%'";

            try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    int productId = rs.getInt("id");
                    int categoryId = rs.getInt("c_id");
                    String name = rs.getString("name");
                    String image = rs.getString("image");
                    float price = rs.getFloat("price");
                    float weight = rs.getFloat("weight");
                    String origin = rs.getString("origin");
                    String quality = rs.getString("quality");
                    String description = rs.getString("description");

                    Product product = new Product(productId, categoryId, name, image, price, weight, origin, quality, description);
                    products.add(product);

                }
            } catch (Exception e) {

            }

        } catch (Exception e) {

        }
        return products;
    }

    @Override
    public List<Product> getProductsSort(String method) {
        List<Product> products = new ArrayList<>();
        try {

            Connection conn = getConnection();
            // Hash the password using SHA-256
            String sql = "select * from products \n"
                    + "order by " + method;
            try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    int productId = rs.getInt("id");
                    int categoryId = rs.getInt("c_id");
                    String name = rs.getString("name");
                    String image = rs.getString("image");
                    float price = rs.getFloat("price");
                    float weight = rs.getFloat("weight");
                    String origin = rs.getString("origin");
                    String quality = rs.getString("quality");
                    String description = rs.getString("description");

                    Product product = new Product(productId, categoryId, name, image, price, weight, origin, quality, description);
                    products.add(product);

                }
            } catch (Exception e) {

            }

        } catch (Exception e) {

        }
        return products;
    }

    @Override
    public void insertProduct(Product product) {
        try {
            Connection conn = getConnection();
            String sql = "INSERT INTO products (c_id, name, image, price, weight, origin, quality, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, product.getC_id());
                pstmt.setString(2, product.getName());
                pstmt.setString(3, product.getImage());
                pstmt.setDouble(4, product.getPrice());
                pstmt.setDouble(5, product.getWeight());
                pstmt.setString(6, product.getOrigin());
                pstmt.setString(7, product.getQuality());
                pstmt.setString(8, product.getDescription());
                pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProduct(int id, Product product) {
        try {
            Connection conn = getConnection();
            // Hash the password using SHA-256
            String sql = "UPDATE products SET c_id = ?, name = ?, image = ?, price = ?, weight = ?, origin = ?, quality = ?, description = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, product.getC_id());
                pstmt.setString(2, product.getName());
                pstmt.setString(3, product.getImage());
                pstmt.setDouble(4, product.getPrice());
                pstmt.setDouble(5, product.getWeight());
                pstmt.setString(6, product.getOrigin());
                pstmt.setString(7, product.getQuality());
                pstmt.setString(8, product.getDescription());
                pstmt.setInt(9, id);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Product updated successfully.");
                } else {
                    System.out.println("No product found with the specified ID.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProduct(int id) {
        try {
            Connection conn = getConnection();
            String sql = "DELETE FROM products WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Product deleted successfully.");
                } else {
                    System.out.println("No product found with the specified ID.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
