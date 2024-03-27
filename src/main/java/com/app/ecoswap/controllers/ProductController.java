package com.app.ecoswap.controllers;

import com.app.ecoswap.models.Product;
import com.app.ecoswap.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){

        Product productCreated = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable Long id, @RequestBody Product productRequest){
        Product productUpdated = productService.updateProductById(id, productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(productUpdated);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body("Producto eliminado con exito");
    }

}
