package com.ecoswap.ecoswap.user.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.ecoswap.ecoswap.user.models.dto.UserDtoRequest;
import com.ecoswap.ecoswap.user.models.entities.Role;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecoswap.ecoswap.product.models.dto.ProductDTO;
import com.ecoswap.ecoswap.user.exceptions.UserNotFoundException;
import com.ecoswap.ecoswap.user.models.dto.UserDTO;
import com.ecoswap.ecoswap.user.models.entities.User;
import com.ecoswap.ecoswap.user.repositories.UserRepository;
import com.ecoswap.ecoswap.user.services.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
        .map(user -> new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getAddress(),
            user.getCellphoneNumber()
        ))
        .collect(Collectors.toList());

    }

    @Override
    public void createUser(UserDtoRequest userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAddress(userDTO.getAddress());
        user.setCellphoneNumber(userDTO.getCellphoneNumber());
        user.setRole(Role.valueOf("USER"));
        userRepository.save(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getEmail(), user.getCellphoneNumber());
    }

    @Override
    public UserDTO updateUserById(Long id, UserDTO updatedUserDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        user.setName(updatedUserDTO.getName());
        user.setEmail(updatedUserDTO.getEmail());
        user.setAddress(updatedUserDTO.getAddress());
        user.setCellphoneNumber(updatedUserDTO.getCellphoneNumber());

        User updatedUser = userRepository.save(user);
        return new UserDTO(updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail(),
                updatedUser.getAddress(), updatedUser.getCellphoneNumber());

    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        userRepository.delete(user);
    }

    @Override
    public UserDTO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User usuarioAutenticado = (User) auth.getPrincipal();
        if (usuarioAutenticado == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(usuarioAutenticado.getId());
        userDTO.setName(usuarioAutenticado.getName());
        userDTO.setEmail(usuarioAutenticado.getEmail());

        return userDTO;
    }

    @Override
    public long countUsers() {
        return userRepository.count();
    }

}
