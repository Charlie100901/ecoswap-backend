package com.ecoswap.ecoswap.product.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoswap.ecoswap.product.models.dto.ProductDTO;
import com.ecoswap.ecoswap.product.services.ProductService;

@RestController
@RequestMapping("api/v1/")
public class ProductController {

    @Autowired
    public ProductService productService;

    @PostMapping("product")
    public void create(ProductDTO productDTO){
        productService.createProduct(productDTO);
    }

    @GetMapping("product")
    public List<ProductDTO> findAllProducts(){
        return productService.findAll();
    }

    @GetMapping("/product/{id}")
    public ProductDTO getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }
}
