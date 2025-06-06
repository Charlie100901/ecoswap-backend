package com.ecoswap.ecoswap.user.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoRequest {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String address;
    private String cellphoneNumber;
}
