package mminc.netask.model;

import mminc.netask.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    void deleteProduct(Long id);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Product updateProduct(Long id, Product productDetails);
}
