package com.app.ecoswap.controllers;

import com.app.ecoswap.models.User;
import com.app.ecoswap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public ArrayList<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("user/{id}")
    public Optional<User> getUserById(@PathVariable Long id)  {
        return userService.getUserById(id);
    }

    @PostMapping("user/create")
    public ResponseEntity<String> createUser(@RequestBody User user){
        if(!userService.checkEmailExists(user)){
            userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(" usuario creado");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("correo electronico ya existe");
        }
    }

    @PutMapping("user/{id}/update")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        User userUpdated = userService.updateUserById(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }

    @DeleteMapping("user/{id}/delete")
    public String deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}
