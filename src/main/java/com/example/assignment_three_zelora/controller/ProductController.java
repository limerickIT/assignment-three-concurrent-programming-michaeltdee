package com.example.assignment_three_zelora.controller;

import org.springframework.ui.Model;
import com.example.assignment_three_zelora.model.entitys.Product;
import com.example.assignment_three_zelora.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String searchForm() {
        return "search"; // show the search page
    }

    @GetMapping("/results")
    public String searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Model model) {

        List<Product> products = productService.search(name, category, minPrice, maxPrice);
        model.addAttribute("products", products);

        return "search";
    }

    @GetMapping("/product/{id}")
    public String viewProduct(@PathVariable int id, Model model) {

        Product product = productService.getProductDetail(id);
        model.addAttribute("product", product);

        return "product";
    }

}
