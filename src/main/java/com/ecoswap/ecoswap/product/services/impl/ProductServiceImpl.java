package com.ecoswap.ecoswap.product.services.impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.ecoswap.ecoswap.product.models.dto.ProductDTO;
import com.ecoswap.ecoswap.product.services.ProductService;

public class ProductServiceImpl implements ProductService{

    @Override
    public void create(ProductDTO productDTO) {
        System.out.println("Crear");
    }

    @Override
    public List<ProductDTO> findAll() {
        List<ProductDTO> list = Arrays
                .asList(
                    new ProductDTO(1L, "Botella Reutilizable", "Botella de acero inoxidable", "Accesorios", "Disponible", "Nuevo", "botella.jpg", LocalDate.of(2023, 1, 15)),
                    new ProductDTO(2L, "Bolsa Ecológica", "Bolsa hecha de materiales reciclados", "Accesorios", "Disponible", "Nuevo", "bolsa.jpg", LocalDate.of(2023, 2, 20)),
                    new ProductDTO(3L, "Cepillo de Bambú", "Cepillo dental ecológico", "Higiene", "Disponible", "Nuevo", "cepillo.jpg", LocalDate.of(2023, 3, 10)),
                    new ProductDTO(4L, "Cafetera Reutilizable", "Cafetera de acero inoxidable", "Electrodomésticos", "Disponible", "Usado", "cafetera.jpg", LocalDate.of(2022, 8, 5)),
                    new ProductDTO(5L, "Taza de Cerámica", "Taza hecha a mano con materiales sostenibles", "Hogar", "Disponible", "Nuevo", "taza.jpg", LocalDate.of(2023, 4, 30)),
                    new ProductDTO(6L, "Compostera", "Contenedor para compostaje en casa", "Jardinería", "Disponible", "Nuevo", "compostera.jpg", LocalDate.of(2023, 6, 12))
                );

        return list;
    }

}
