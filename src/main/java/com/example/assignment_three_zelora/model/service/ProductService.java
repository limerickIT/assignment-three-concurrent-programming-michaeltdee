package com.example.assignment_three_zelora.model.service;

import com.example.assignment_three_zelora.model.entitys.Product;
import com.example.assignment_three_zelora.model.entitys.Review;
import com.example.assignment_three_zelora.model.repos.ProductRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    //search method function
    public List<Product> searchProducts(String name,
                                        String category,
                                        Double minPrice,
                                        Double maxPrice,
                                        String keyword,
                                        Boolean recent) {

        Date recentDate = null;

        if (Boolean.TRUE.equals(recent)) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -7);
            recentDate = cal.getTime();
        }

        return productRepository.searchProducts(
                name,
                category,
                minPrice,
                maxPrice,
                keyword,
                recent,
                recentDate
        );
    }


    //Method for product drill down
    public Product getProductDetail(int id) {
        return productRepository.findProductDetail(id);
    }


    public List<Product> search(String name,
                                String category,
                                Double minPrice,
                                Double maxPrice,
                                String keyword,
                                Boolean recent) {

        Date recentDate = null;

        if (Boolean.TRUE.equals(recent)) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -7);
            recentDate = cal.getTime();
        }

        return productRepository.searchProducts(
                name,
                category,
                minPrice,
                maxPrice,
                keyword,
                recent,
                recentDate
        );
    }

    public List<String> getAdditionalImages(int productId, String featureImage) {
        String basePath = "src/main/resources/static/images/large/" + productId + "/";
        File folder = new File(basePath);

        List<String> images = new ArrayList<>();

        if (folder.exists() && folder.isDirectory()) {
            for (File f : folder.listFiles()) {
                String filename = f.getName();

                // Ignore the feature image & only include real images
                if (!filename.equals(featureImage)
                        && (filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".jpeg"))) {
                    images.add(filename);
                }
            }
        }

        return images;
    }

    //Method for getting avg review rating
    public Double getAverageRating(Product product) {
        if (product.getReviewList() == null || product.getReviewList().isEmpty()) {
            return null; // No reviews
        }

        return product.getReviewList().stream()
                .filter(r -> r.getRating() != null)
                .mapToInt(r -> r.getRating())
                .average()
                .orElse(0.0);
    }

    public List<Review> getFilteredReviews(Product product) {
        if (product.getReviewList() == null) {
            return List.of();
        }

        return product.getReviewList()
                .stream()
                .filter(r -> r.getRating() != null && r.getRating() >= 3)
                .toList();
    }

    public List<Product> getProductsByIds(List<Integer> ids) {
        return productRepository.findAllById(ids);
    }

}
