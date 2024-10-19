package com.ecoswap.ecoswap.product.services;

import java.util.List;

import com.ecoswap.ecoswap.product.models.dto.ProductDTO;
import com.ecoswap.ecoswap.user.models.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    List<ProductDTO> findAll();
    ProductDTO createProduct(ProductDTO productDTO, MultipartFile image);
    ProductDTO updateProductById(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
    ProductDTO getProductById(Long id);

    List<ProductDTO> getProductsByCategory(String category);
    List<ProductDTO> getProductsByUser();

    void markProductsAsInactiveFromCompletedExchanges();

}
