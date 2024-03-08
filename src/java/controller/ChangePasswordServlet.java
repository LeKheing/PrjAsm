/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UserDAO;
import dao.UserDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author admin
 */
@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/change-pass"})
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String oldPassword = request.getParameter("oldPass");
        String newPassword = request.getParameter("newPass");
        String confirmNewPassword = request.getParameter("conNewPass");

        HttpSession session = request.getSession();

        UserDAO userDAO = new UserDAOImpl();
        User user = (User) session.getAttribute("currentUser");

        if (hashPassword(oldPassword).equals(user.getPasswordHash())) {
            if (newPassword.equals(confirmNewPassword)) {
                if (!hashPassword(newPassword).equals(hashPassword(oldPassword))) {
                    
                    userDAO.updateUserPass(user.getEmail(), newPassword);
                    
                    user.setPasswordHash(hashPassword(newPassword));
                    session.setAttribute("currentUser", user);
                    request.setAttribute("success", "Change successfully");
                    request.getRequestDispatcher("info.jsp").forward(request, response);
                } else {
                    request.setAttribute("err", "New password must be different from old password");
                    request.getRequestDispatcher("info.jsp").forward(request, response);
                }

            } else {
                request.setAttribute("err", "New password and confirm password do not match.");
                request.getRequestDispatcher("info.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("err", "Old password is incorrect.");
            request.getRequestDispatcher("info.jsp").forward(request, response);
        }

        
    }

    private String hashPassword(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ChangePasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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

}
