package com.ecoswap.ecoswap.user.services.impl;

import java.util.Arrays;
import java.util.List;

import com.ecoswap.ecoswap.user.models.dto.UserDTO;
import com.ecoswap.ecoswap.user.services.UserService;

public class UserServiceImpl implements UserService{

    @Override
    public void create(UserDTO userDTO) {
        System.out.println("Crear");
    }

    @Override
    public List<UserDTO> findAll() {
        List<UserDTO> list = Arrays
            .asList(
                new UserDTO(1L, "Carlos Mendoza", "carlos.mendoza@example.com", "password123", "Calle 10, Cartagena", 300127),
                new UserDTO(2L, "Ana López", "ana.lopez@example.com", "password456", "Avenida Siempre Viva, Medellín", 3104321),
                new UserDTO(3L, "Luis Rodríguez", "luis.rodriguez@example.com", "password789", "Carrera 7, Bogotá", 3209876),
                new UserDTO(4L, "Marta Sánchez", "marta.sanchez@example.com", "password012", "Calle 5, Barranquilla", 3012678),
                new UserDTO(5L, "Pedro Ramírez", "pedro.ramirez@example.com", "password345", "Avenida 3, Cali", 31187632),
                new UserDTO(6L, "Laura Gómez", "laura.gomez@example.com", "password678", "Carrera 50, Bucaramanga", 31596543)
            );

        return list;
    }

}
