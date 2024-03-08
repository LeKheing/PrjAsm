/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CategoryDAO;
import dao.CategoryDAOImpl;
import dao.ProductDAO;
import dao.ProductDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Category;
import model.Product;

/**
 *
 * @author admin
 */
@WebServlet(name = "AdminAddProductServlet", urlPatterns = {"/add-product"})
public class AdminAddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoryDAO categoryDAO = new CategoryDAOImpl();
        List<Category> categories = categoryDAO.getAllCategories();

        request.setAttribute("cats", categories);

        request.getRequestDispatcher("product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String img = request.getParameter("product_img");
        String cate = request.getParameter("cate");
        String price = request.getParameter("price");
        String weight = request.getParameter("weight");
        String origin = request.getParameter("origin");
        String quality = request.getParameter("quality");
        String description = request.getParameter("description");
        
        int cid = Integer.parseInt(cate);
        
        double priceD = Double.parseDouble(price);
        double weightD = Double.parseDouble(weight);
        
        Product product = new Product(0, cid, name, img, priceD, weightD, origin, quality, description);
        ProductDAO productDAO = new ProductDAOImpl();
        
        productDAO.insertProduct(product);
        response.sendRedirect("admin-product");
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
