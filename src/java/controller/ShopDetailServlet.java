/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProductDAOImpl;
import dao.ReviewDAO;
import dao.ReviewDAOImpl;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Product;
import model.Review;

/**
 *
 * @author admin
 */
@WebServlet(name = "ShopDetailServlet", urlPatterns = {"/detail"})
public class ShopDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = (String) request.getParameter("id");
        int idPro = Integer.parseInt(id);

        ProductDAOImpl proDAO = new ProductDAOImpl();
        ReviewDAO reviewDAO = new ReviewDAOImpl();
        
        List<Review> reviews = reviewDAO.getReviewsByP_id(idPro);
        Product product = proDAO.getProduct(idPro);

        request.setAttribute("product", product);
        request.setAttribute("reviews", reviews);

        request.getRequestDispatcher("shop-detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = (String) request.getParameter("id");
        int idPro = Integer.parseInt(id);

        ProductDAOImpl proDAO = new ProductDAOImpl();
        ReviewDAO reviewDAO = new ReviewDAOImpl();
        List<Review> reviews = reviewDAO.getReviewsByP_id(idPro);

        Product product = proDAO.getProduct(idPro);

        request.setAttribute("product", product);
        request.setAttribute("reviews", reviews);

        request.getRequestDispatcher("shop-detail.jsp").forward(request, response);
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
