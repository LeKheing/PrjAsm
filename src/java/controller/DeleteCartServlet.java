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
import java.io.PrintWriter;
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
@WebServlet(name = "DeleteCartServlet", urlPatterns = {"/delete-cart"})
public class DeleteCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = (String) request.getParameter("o_id");
        double sum = 0;
        
        OrderDAO orderDAO = new OrderDAOImpl();
        orderDAO.deleteOrder(Integer.parseInt(id));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");
        int size = orderDAO.getAllOrders(user.getId()).size();
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
        session.setAttribute("size", size);

        response.sendRedirect("cart");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
