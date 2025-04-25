package com.ecoswap.ecoswap.user.models.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterDTO {
    @NotBlank(message = "El campo nombre es requerido")
    private String name;
    @NotBlank(message = "El campo email es requerido")
    @Email(message = "Debe escribir un email valido")
    private String email;
    @NotBlank(message = "El campo contraseña es requerido")
    private String password;
    @NotBlank(message = "El campo dirección es requerido")
    private String address;
    @NotBlank(message = "El número de teléfono es requerido")
    @Pattern(regexp = "\\d{10}", message = "El número de teléfono debe tener exactamente 10 dígitos numéricos")
    private String cellphoneNumber;
}
