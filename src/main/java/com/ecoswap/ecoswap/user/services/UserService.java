package com.ecoswap.ecoswap.user.services;

import java.util.List;

import com.ecoswap.ecoswap.product.models.dto.ProductDTO;
import com.ecoswap.ecoswap.user.models.dto.UserDTO;
import com.ecoswap.ecoswap.user.models.dto.UserDtoRequest;

public interface UserService {
    void createUser(UserDtoRequest userDTO);
    UserDTO getUserById(Long id);
    List<UserDTO> findAll();
    UserDTO updateUserById(Long id, UserDTO user);
    void deleteUser(Long id);
    UserDTO getCurrentUser();
    long countUsers();

}
