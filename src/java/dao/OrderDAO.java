/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import model.Order;

/**
 *
 * @author admin
 */
public interface OrderDAO {
    boolean insertOrder(Order order);
    boolean updateQuantity(int orderId, int additionalQuantity);
    Order getOrder(int uId, int pId);
    List<Order> getAllOrders(int uId);
    List<Integer> getPIdsByUId(int uId);
    boolean deleteOrder(int id);
    boolean deleteOrderByUser(int u_id);
}
