package com.app.ecoswap.services;

import com.app.ecoswap.exceptions.NotUserFoundException;
import com.app.ecoswap.models.Product;
import com.app.ecoswap.models.User;
import com.app.ecoswap.repositories.IUserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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

    public Optional<User> getUserById(Long id)  {
        return userRepository.findById(id);
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
                existingUser.setCellphone_number(user.getCellphone_number());
                return userRepository.save(existingUser);
            }else{
                throw new NotUserFoundException("No se encontró un usuario");
            }
        }catch (NotUserFoundException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public String deleteUser(Long id){
        userRepository.deleteById(id);
        return "Usuario Eliminado Correctamente";
    }







}
