/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Order;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class OrderDAOImpl extends DBContext implements OrderDAO {

    @Override
    public boolean insertOrder(Order order) {
        try {

            Connection conn = getConnection();
            // Hash the password using SHA-256
            String sql = "insert into orders(u_id,p_id,quantity)\n"
                    + "values(?,?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, order.getUId());
                pstmt.setInt(2, order.getPId());
                pstmt.setInt(3, order.getQuantity());

                // Execute the insert statement
                int rowsAffected = pstmt.executeUpdate();

                // Check if the insertion was successful
                return rowsAffected > 0;
            }
        } catch (Exception e) {

        }

        return false;
    }

    @Override
    public boolean updateQuantity(int orderId, int additionalQuantity) {
        try {
            Connection conn = getConnection();
            String sql = "UPDATE orders SET quantity = quantity + ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, additionalQuantity);
                pstmt.setInt(2, orderId);

                // Execute the update statement
                int rowsAffected = pstmt.executeUpdate();

                // Check if the update was successful
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public Order getOrder(int uId, int pId) {
        Order order = null;
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM orders WHERE u_id = ? AND p_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, uId);
                pstmt.setInt(2, pId);

                // Execute the select statement
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int orderId = rs.getInt("id");
                        int quantity = rs.getInt("quantity");
                        // Retrieve other order information as needed

                        // Create an Order object with the retrieved data
                        order = new Order(orderId, uId, pId, quantity);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return order;
    }

    @Override
    public List<Integer> getPIdsByUId(int uId) {
        List<Integer> pIds = new ArrayList<>();
        try {
            Connection conn = getConnection();
            String sql = "SELECT DISTINCT p_id FROM orders WHERE u_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, uId);

                // Execute the select statement
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int pId = rs.getInt("p_id");
                        pIds.add(pId);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pIds;
    }

    public List<Order> getAllOrders(int uId) {
        List<Order> orders = new ArrayList<>();
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM orders WHERE u_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, uId);

                // Execute the select statement
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        int pId = rs.getInt("p_id");
                        int quantity = rs.getInt("quantity");

                        // Create an Order object
                        Order order = new Order(id, uId, pId, quantity);

                        // Add the order to the list
                        orders.add(order);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public boolean deleteOrder(int id) {
        List<Order> orders = new ArrayList<>();
        try {
            Connection conn = getConnection();
            String sql = "delete from orders where id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);

                // Execute the update statement
                int rowsAffected = pstmt.executeUpdate();

                // Check if the update was successful
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteOrderByUser(int u_id) {
        try {
            Connection conn = getConnection();
            String sql = "DELETE FROM orders WHERE u_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, u_id);

                // Execute the update statement
                int rowsAffected = pstmt.executeUpdate();

                // Check if the update was successful
                return rowsAffected > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
