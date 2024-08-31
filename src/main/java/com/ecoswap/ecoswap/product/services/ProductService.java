package com.ecoswap.ecoswap.product.services;

import java.util.List;

import com.ecoswap.ecoswap.product.models.dto.ProductDTO;

public interface ProductService {
    void create(ProductDTO productDTO);
    List<ProductDTO> findAll();
}
