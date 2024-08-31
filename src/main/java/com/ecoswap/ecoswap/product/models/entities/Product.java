package com.ecoswap.ecoswap.product.models.entities;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String productStatus;
    private String conditionProduct;
    private String imageProduct;
    private LocalDate releaseDate;

}
