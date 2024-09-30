package com.ecoswap.ecoswap.user.models.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterDTO {
    private String name;
    private String email;
    private String password;
    private String address;
    private int cellphoneNumber;
}
