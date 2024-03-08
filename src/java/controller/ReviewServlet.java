/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProductDAOImpl;
import dao.ReviewDAO;
import dao.ReviewDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Product;
import model.Review;
import model.User;

/**
 *
 * @author admin
 */
@WebServlet(name = "ReviewServlet", urlPatterns = {"/review"})
public class ReviewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int p_id = Integer.parseInt(request.getParameter("p_id"));
        ReviewDAO reviewDAO = new ReviewDAOImpl();
        List<Review> reviews = reviewDAO.getReviewsByP_id(p_id);

        ProductDAOImpl proDAO = new ProductDAOImpl();

        Product product = proDAO.getProduct(p_id);

        request.setAttribute("product", product);
        request.setAttribute("reviews", reviews);

        request.getRequestDispatcher("shop-detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");

        if (user == null) {
            request.getRequestDispatcher("401.jsp").forward(request, response);
        } else {
            int p_id = Integer.parseInt(request.getParameter("p_id"));
            int u_id = user.getId();
            String u_name = user.getFirstName() + " " + user.getLastName();
            String review_text = request.getParameter("review");

            Review review = new Review(0, u_id, p_id, review_text, u_name);

            ReviewDAO reviewDAO = new ReviewDAOImpl();
            reviewDAO.insertReview(review);
            List<Review> reviews = reviewDAO.getReviewsByP_id(p_id);

            ProductDAOImpl proDAO = new ProductDAOImpl();

            Product product = proDAO.getProduct(p_id);

            request.setAttribute("product", product);
            request.setAttribute("reviews", reviews);

            request.getRequestDispatcher("shop-detail.jsp").forward(request, response);
        }
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
