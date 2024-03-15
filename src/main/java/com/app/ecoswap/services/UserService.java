package com.app.ecoswap.services;

import com.app.ecoswap.exceptions.NotUserFoundException;
import com.app.ecoswap.models.User;
import com.app.ecoswap.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public ArrayList<User> getUsers(){
        return (ArrayList<User>) userRepository.findAll();
    }

    public User getUserById(Long id)  {
        return userRepository.findById(id)
                .orElseThrow(()->new NotUserFoundException("Usuario no existe en la base de datos"));
    }

    public boolean checkEmailExists(User request){
        Optional<User> userOptional = userRepository.findUserByEmail(request.getEmail());
        return userOptional.isPresent();
    }

    public void createUser(User user){
        userRepository.save(user);
    }

    public User updateUserById(Long id, User user){
        try {
            Optional<User> existingUserOptional = userRepository.findById(id);
            if(existingUserOptional.isPresent()){
                User existingUser = existingUserOptional.get();
                existingUser.setName(user.getName());
                existingUser.setAddress(user.getAddress());
                existingUser.setPassword(user.getPassword());
                existingUser.setCellphoneNumber(user.getCellphoneNumber());
                return userRepository.save(existingUser);
            }else{
                throw new NotUserFoundException("No se encontró un usuario");
            }
        }catch (NotUserFoundException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public ResponseEntity<String> deleteUser(Long id){
        try {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Usuario Eliminado exitosamente");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no pudo ser eliminado");
        }
    }







}
