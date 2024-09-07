package com.ecoswap.ecoswap.product.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecoswap.ecoswap.product.exceptions.ProductNotFoundException;
import com.ecoswap.ecoswap.product.models.dto.ProductDTO;
import com.ecoswap.ecoswap.product.models.entities.Product;
import com.ecoswap.ecoswap.product.repositories.ProductRepository;
import com.ecoswap.ecoswap.product.services.ProductService;
import com.ecoswap.ecoswap.user.models.dto.UserDTO;

@Service
public class ProductServicesImpl implements ProductService{

    @Autowired
    public ProductRepository productRepository;

  
    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
        .filter(product -> "activo".equals(product.getProductStatus()))
        .map(product -> new ProductDTO(
            product.getId(),
            product.getTitle(),
            product.getDescription(),
            product.getCategory(),
            product.getConditionProduct(),
            product.getImageProduct(),
            null,
            LocalDate.now()
        ))
        .collect(Collectors.toList());
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createProduct'");
    }

    @Override
    public ProductDTO updateProductById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProductById'");
    }

    @Override
    public void deleteProduct(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProduct'");
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("No existe el producto con id " + id));
    
        return new ProductDTO(
            product.getId(),
            product.getTitle(),
            product.getDescription(),
            product.getCategory(),
            product.getConditionProduct(),
            product.getImageProduct(),
            null,
            product.getReleaseDate()
        );
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String category) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductsByCategory'");
    }

    @Override
    public List<ProductDTO> getProductsByUser(UserDTO user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductsByUser'");
    }

}
