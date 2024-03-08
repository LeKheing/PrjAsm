/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import model.Review;

/**
 *
 * @author admin
 */
public interface ReviewDAO {
    boolean insertReview(Review review);
    List<Review> getReviewsByP_id(int p_id);
    boolean updateU_name(int u_id, String u_name);
}
