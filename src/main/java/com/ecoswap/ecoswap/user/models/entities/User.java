package com.ecoswap.ecoswap.user.models.entities;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String address;
    private int cellphoneNumber;
}
