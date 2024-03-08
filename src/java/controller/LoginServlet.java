/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.OrderDAO;
import dao.OrderDAOImpl;
import dao.UserDAOImpl;
import java.io.IOException;
import java.security.MessageDigest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author bravee06
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();
        User user = authenticateUser(email, password);
        if (user != null) {
            session.setAttribute("currentUser", user);
            OrderDAO orderDAO = new OrderDAOImpl();
            int size = orderDAO.getAllOrders(user.getId()).size();
            session.setAttribute("size", size);
            System.out.println(user.getIsAdmin());
            
            if (user.getIsAdmin()) {
                response.sendRedirect("admin-user");
            } else {
                response.sendRedirect("home");
            }

        } else {

            request.setAttribute("errorMessage", "Username or password may be wrong ! Please login again !");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private String hashPassword(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(password.getBytes());

        StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
        for (byte b : encodedHash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    private User authenticateUser(String email, String password) {
        UserDAOImpl udi = new UserDAOImpl();
        User user = udi.findByEmail(email);

        try {
            String passwordHashed = hashPassword(password);
            if (user != null && user.getPasswordHash().equals(passwordHashed)) {
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
