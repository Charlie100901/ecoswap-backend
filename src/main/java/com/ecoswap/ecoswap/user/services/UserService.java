package com.ecoswap.ecoswap.user.services;

import java.util.List;

import com.ecoswap.ecoswap.user.models.dto.UserDTO;

public interface UserService {
    void create(UserDTO userDTO);
    List<UserDTO> findAll();
}
