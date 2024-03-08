/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.OrderHistory;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class OrderHistoryDAOImpl extends DBContext implements OrderHistoryDAO {

    @Override
    public void insertOrderHisotry(OrderHistory orderHistory) {
        try {
            Connection conn = getConnection();
            String sql = "INSERT INTO order_history (u_id, shipping_address, total_price, order_date, name_order) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, orderHistory.getUserId());
                pstmt.setString(2, orderHistory.getShippingAddress());
                pstmt.setDouble(3, orderHistory.getTotalPrice());

                Date utilDate = orderHistory.getOrderDate();

                // Chuyển đổi sang java.sql.Date
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                pstmt.setDate(4, sqlDate);
                pstmt.setString(5, orderHistory.getNameOrder());

                // Execute the insert statement
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OrderHistory> getAllOrderHistorys() {
        List<OrderHistory> orderHistorys = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM order_history";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        // Tạo đối tượng OrderHistory từ kết quả truy vấn
                        OrderHistory orderHistory = new OrderHistory();
                        orderHistory.setUserId(rs.getInt("u_id"));
                        orderHistory.setShippingAddress(rs.getString("shipping_address"));
                        orderHistory.setTotalPrice(rs.getDouble("total_price"));
                        orderHistory.setOrderDate(rs.getDate("order_date"));
                        orderHistory.setNameOrder(rs.getString("name_order"));

                        // Thêm đối tượng OrderHistory vào danh sách
                        orderHistorys.add(orderHistory);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderHistorys;
    }

}
