package com.ecoswap.ecoswap.user.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoswap.ecoswap.user.models.dto.UserDTO;
import com.ecoswap.ecoswap.user.services.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user")
    public void userCreate(UserDTO userDTO){
        userService.create(userDTO);
    }

    @GetMapping("user")
    public List<UserDTO> findAllUsers(){
        return userService.findAll();
    }

}
