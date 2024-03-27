package com.app.ecoswap.controllers;

import com.app.ecoswap.exceptions.UserNotFoundException;
import com.app.ecoswap.models.User;
import com.app.ecoswap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
    public User getUserById(@PathVariable Long id)  {
        return userService.getUserById(id);
    }

    @PostMapping("user/create")
    public ResponseEntity<String> createUser(@RequestBody User user){
        if (!userService.checkEmailExists(user)) {
            userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Correo electronico ya existe");
        }
    }

    @PutMapping("user/{id}/update")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        User userUpdated = userService.updateUserById(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }

    @DeleteMapping("user/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        try {
            String response = userService.deleteUser(id);
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no se encontró: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error: " + e.getMessage());
        }
    }
}
