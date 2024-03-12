package com.app.ecoswap.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //FOREING KEY = USER_ID

    private String title;
    private String description;
    private String Category;
    private String product_status;
    //IMAGEN DEL ARTICULO

}
