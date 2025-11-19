package com.example.assignment_three_zelora.model.repos;

import com.example.assignment_three_zelora.model.entitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("""
    SELECT p FROM Product p
    JOIN p.categoryId c
    WHERE (:name IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :name, '%')))
      AND (:category IS NULL OR LOWER(c.categoryName) LIKE LOWER(CONCAT('%', :category, '%')))
      AND (:minPrice IS NULL OR p.price >= :minPrice)
      AND (:maxPrice IS NULL OR p.price <= :maxPrice)
      AND (:keyword IS NULL OR CAST(p.description AS string) LIKE CONCAT('%', :keyword, '%'))
      AND (:recent IS NULL OR p.releaseDate >= :recentDate)
""")
    List<Product> searchProducts(
            String name,
            String category,
            Double minPrice,
            Double maxPrice,
            String keyword,
            Boolean recent,
            java.util.Date recentDate
    );




    @Query("""
        SELECT p FROM Product p
        LEFT JOIN FETCH p.categoryId
        LEFT JOIN FETCH p.reviewList
        LEFT JOIN FETCH p.inventoryList
        WHERE p.productId = :id
        """)
    Product findProductDetail(int id);
}
