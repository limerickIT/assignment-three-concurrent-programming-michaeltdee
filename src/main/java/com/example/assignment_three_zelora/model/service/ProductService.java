package com.example.assignment_three_zelora.model.service;

import com.example.assignment_three_zelora.model.entitys.Product;
import com.example.assignment_three_zelora.model.repos.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //create
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    //Get all
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //get one
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    //Update
    public Product updateProduct(Integer id, Product updatedProduct) {
        if (!productRepository.existsById(id)) {
            return null;
        }
        updatedProduct.setProductId(id);
        return productRepository.save(updatedProduct);
    }

    //Delete by id
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    //search method
    public List<Product> search(String name, String category, Double minPrice, Double maxPrice) {
        return productRepository.searchProducts(name, category, minPrice, maxPrice);
    }

    //product detail method
    public Product getProductDetail(int id) {
        return productRepository.findProductDetail(id);
    }
}
