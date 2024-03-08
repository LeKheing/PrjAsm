/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import model.OrderHistory;

/**
 *
 * @author admin
 */
public interface OrderHistoryDAO{
    void insertOrderHisotry(OrderHistory orderHistory);
    List<OrderHistory> getAllOrderHistorys();
}
