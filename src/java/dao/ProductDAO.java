/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import model.Product;

/**
 *
 * @author admin
 */
public interface ProductDAO {
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(int c_id);
    Product getProduct(int id);
    List<Product> getProductsByName(String name);
    List<Product> getProductsSort(String method);
    void insertProduct(Product product);
    void updateProduct(int id, Product product);
    void deleteProduct(int id);
}
