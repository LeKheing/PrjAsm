/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UserDAO;
import dao.UserDAOImpl;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author bravee06
 */
@WebServlet(name = "RegisterAccountServlet", urlPatterns = {"/register"})
public class RegisterAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String agreed = request.getParameter("agreed");
        boolean status = false;
        String errorMessage = "";
        String successMessage = "";
        if (agreed != null) {
            User newUser = new User();
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setEmail(email);
            newUser.setPasswordHash(password);
            newUser.setPhoneNumber(phone);
            UserDAO userDAO = new UserDAOImpl();
            User user = userDAO.findByEmail(email);
            if (user != null) {
                status = false;
                errorMessage = "This Email has been registered";
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                status = true; 
                userDAO.insertUser(firstName, lastName, email, password, phone);
                successMessage = "Register success";
                request.setAttribute("successMessage", successMessage);
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
            

        } else {
            errorMessage = "Please agree with use term";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }

        
        
        
    }

}
