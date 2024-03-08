/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Review;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class ReviewDAOImpl extends DBContext implements ReviewDAO {

    @Override
    public boolean insertReview(Review review) {
        try {
            Connection conn = getConnection();
            String sql = "INSERT INTO review (p_id, u_id, review, u_name) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, review.getP_id());
                pstmt.setInt(2, review.getU_id());
                pstmt.setString(3, review.getReview_text());
                pstmt.setString(4, review.getU_name());

                // Execute the insert statement
                int rowsAffected = pstmt.executeUpdate();

                // Check if the insertion was successful
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception properly, e.g., log or throw it
        }

        return false;

    }

    @Override
    public List<Review> getReviewsByP_id(int p_id) {
        List<Review> reviews = new ArrayList<>();

        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM review WHERE p_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, p_id);

                // Execute the query
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int reviewId = rs.getInt("id");
                        int productId = rs.getInt("p_id");
                        int userId = rs.getInt("u_id");
                        String reviewText = rs.getString("review");
                        String u_name = rs.getString("u_name");
                        // Create a new Review object and add it to the list
                        Review review = new Review(reviewId, productId, userId, reviewText, u_name);
                        reviews.add(review);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception properly, e.g., log or throw it
        }

        return reviews;
    }

    @Override
    public boolean updateU_name(int u_id, String u_name) {
        try {
            Connection conn = getConnection();
            String sql = "UPDATE review SET u_name = ? WHERE u_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, u_name);
                pstmt.setInt(2, u_id);

                // Execute the update statement
                int rowsAffected = pstmt.executeUpdate();

                // Check if the update was successful
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception properly, e.g., log or throw it
        }

        return false;
    }

}
