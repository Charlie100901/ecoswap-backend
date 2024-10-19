package com.ecoswap.ecoswap.user.services;

import java.util.List;

import com.ecoswap.ecoswap.product.models.dto.ProductDTO;
import com.ecoswap.ecoswap.user.models.dto.UserDTO;

public interface UserService {
    void createUser(UserDTO userDTO);
    UserDTO getUserById(Long id);
    List<UserDTO> findAll();
    ProductDTO updateUserById(Long id);
    void deleteUser(Long id);
    UserDTO getCurrentUser();

}
