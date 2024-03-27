package com.app.ecoswap.services;

import com.app.ecoswap.exceptions.UserNotFoundException;
import com.app.ecoswap.models.User;
import com.app.ecoswap.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
                .orElseThrow(()->new UserNotFoundException("Usuario no existe en la base de datos"));
    }

    public boolean checkEmailExists(User request){
        Optional<User> userOptional = userRepository.findUserByEmail(request.getEmail());
        return userOptional.isPresent();
    }

    public void createUser(User user){
        userRepository.save(user);
    }

    public User updateUserById(Long id, User user){
            Optional<User> existingUserOptional = userRepository.findById(id);
            if(existingUserOptional.isPresent()){
                User existingUser = existingUserOptional.get();
                existingUser.setName(user.getName());
                existingUser.setAddress(user.getAddress());
                existingUser.setPassword(user.getPassword());
                existingUser.setCellphoneNumber(user.getCellphoneNumber());
                return userRepository.save(existingUser);
            }else {
                throw new UserNotFoundException("No se encontró un usuario");
            }
    }

    public String deleteUser(Long id){
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
            return "Usuario eliminado exitosamente";
        } else {
            throw new UserNotFoundException("No se encontró un usuario con el ID especificado: " + id);
        }
    }







}
