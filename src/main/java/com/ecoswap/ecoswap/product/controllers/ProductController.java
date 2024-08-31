package com.ecoswap.ecoswap.product.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoswap.ecoswap.product.models.dto.ProductDTO;
import com.ecoswap.ecoswap.product.services.ProductService;

@RestController
public class ProductController {

    public final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("product")
    public void create(ProductDTO productDTO){
        productService.create(productDTO);
    }

    @GetMapping("product")
    public List<ProductDTO> findAllProducts(){
        return productService.findAll();
    }
}
