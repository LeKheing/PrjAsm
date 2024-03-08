/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.OrderDAO;
import dao.OrderDAOImpl;
import dao.OrderHistoryDAO;
import dao.OrderHistoryDAOImpl;
import dao.ProductDAO;
import dao.ProductDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Cart;
import model.Order;
import model.OrderHistory;
import model.Product;
import model.User;

/**
 *
 * @author admin
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        double sum = 0;
        User user = (User) session.getAttribute("currentUser");
        OrderDAO orderDAO = new OrderDAOImpl();
        List<Cart> carts = new ArrayList<>();
        int uid = user.getId();
        List<Integer> pIds = orderDAO.getPIdsByUId(uid);
        List<Order> orders = orderDAO.getAllOrders(uid);

        ProductDAO productDAO = new ProductDAOImpl();

        for (int i = 0; i < orders.size(); i++) {
            Product product = productDAO.getProduct(pIds.get(i));
            Cart cart = new Cart(product.getName(), product.getImage(), product.getPrice(), orders.get(i).getQuantity());
            sum += product.getPrice() * orders.get(i).getQuantity();
            cart.setO_id(orders.get(i).getId());
            carts.add(cart);
        }

        request.setAttribute("carts", carts);
        request.setAttribute("sum", sum);
        request.getRequestDispatcher("checkout.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String total = request.getParameter("total");

        double totalP = Double.parseDouble(total);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");
        OrderDAO orderDAO = new OrderDAOImpl();
        orderDAO.deleteOrderByUser(user.getId());

        OrderHistoryDAO orderHistoryDAO = new OrderHistoryDAOImpl();

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        OrderHistory orderHistory = new OrderHistory(0, user.getId(), address, totalP, phone, today, firstName + " " + lastName);

        orderHistoryDAO.insertOrderHisotry(orderHistory);
        session.setAttribute("size", 0);
        response.sendRedirect("home");
        
    }

}
