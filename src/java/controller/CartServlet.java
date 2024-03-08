/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.OrderDAO;
import dao.OrderDAOImpl;
import dao.ProductDAO;
import dao.ProductDAOImpl;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Cart;
import model.Order;
import model.Product;
import model.User;

/**
 *
 * @author admin
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        double sum = 0;
        User user = (User) session.getAttribute("currentUser");
        OrderDAO orderDAO = new OrderDAOImpl();
        List<Cart> carts = new ArrayList<>();
        if (user == null) {
            request.getRequestDispatcher("401.jsp").forward(request, response);
        } else {
            int uid = user.getId();
            List<Integer> pIds = orderDAO.getPIdsByUId(uid);
            List<Order> orders = orderDAO.getAllOrders(uid);

            ProductDAO productDAO = new ProductDAOImpl();

            
            
            for (int i = 0; i < orders.size(); i++) {
                Product product = productDAO.getProduct(pIds.get(i));
                Cart cart = new Cart(product.getName(), product.getImage(), product.getPrice(), orders.get(i).getQuantity());
                sum += product.getPrice()*orders.get(i).getQuantity();
                cart.setO_id(orders.get(i).getId());
                carts.add(cart);
            }
        }

        request.setAttribute("carts", carts);
        request.setAttribute("sum", sum);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        double sum = 0;
        User user = (User) session.getAttribute("currentUser");
        OrderDAO orderDAO = new OrderDAOImpl();
        List<Cart> carts = new ArrayList<>();
        if (user == null) {
            request.getRequestDispatcher("401.jsp").forward(request, response);
        } else {
            int uid = user.getId();
            int pid = Integer.parseInt(request.getParameter("proId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            Order order = new Order(0, uid, pid, quantity);

            if (orderDAO.getOrder(uid, pid) != null) {
                orderDAO.updateQuantity(orderDAO.getOrder(uid, pid).getId(), quantity);
            } else {
                orderDAO.insertOrder(order);
            }
            List<Integer> pIds = orderDAO.getPIdsByUId(uid);
            List<Order> orders = orderDAO.getAllOrders(uid);

            ProductDAO productDAO = new ProductDAOImpl();

            for (int i = 0; i < orders.size(); i++) {
                Product product = productDAO.getProduct(pIds.get(i));
                Cart cart = new Cart(product.getName(), product.getImage(), product.getPrice(), orders.get(i).getQuantity());
                sum += product.getPrice()*((double) orders.get(i).getQuantity());
                cart.setO_id(orders.get(i).getId());
                carts.add(cart);
            }
        }

        int size = orderDAO.getAllOrders(user.getId()).size();
        System.out.println(sum);
        session.setAttribute("size", size);
        request.setAttribute("carts", carts);
        request.setAttribute("sum", sum);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
