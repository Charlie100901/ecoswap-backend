package com.ecoswap.ecoswap.product.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductResponseDTO {
    private List<ProductDTO> products;
    private long totalPages;

}
