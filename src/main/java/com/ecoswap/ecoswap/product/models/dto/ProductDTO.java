package com.ecoswap.ecoswap.product.models.dto;

import java.time.LocalDate;

import com.ecoswap.ecoswap.user.models.dto.UserDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    @NotBlank(message = "El campo titulo es requerido")
    private String title;
    @NotBlank(message = "El campo descripción es requerido")
    @Size(min = 20, message = "La descripción debe ser minimo de 20 caracteres")
    private String description;
    @NotBlank(message = "El campo categoría es requerido")
    private String category;
    @NotBlank(message = "El campo condición del producto es requerido")
    private String conditionProduct;
    private String imageProduct;
    private LocalDate releaseDate;
    private UserDTO user;
}
