package com.ecoswap.ecoswap.user.controllers;

import java.util.List;

import com.ecoswap.ecoswap.user.models.dto.UserDtoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecoswap.ecoswap.user.models.dto.UserDTO;
import com.ecoswap.ecoswap.user.services.UserService;

@RestController
@RequestMapping("api/v1/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user")
    public void userCreate(@RequestBody UserDtoRequest userDTO){
        userService.createUser(userDTO);
    }

    @GetMapping("user")
    public List<UserDTO> findAllUsers(){
        return userService.findAll();
    }

    @GetMapping("user/{id}")
    public UserDTO getUserById(@PathVariable Long id)  {
        return userService.getUserById(id);
    }

    @GetMapping("user/me")
    public UserDTO getCurrentUser () {
        return userService.getCurrentUser();
    }

    @GetMapping("users/count")
    public Long countUsers(){
        return userService.countUsers();
    }

    @PutMapping("user/{id}")
    public ResponseEntity<UserDTO> updateUserById(
            @PathVariable Long id,
            @RequestBody UserDTO updatedUserDTO) {
        UserDTO updatedUser = userService.updateUserById(id, updatedUserDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
