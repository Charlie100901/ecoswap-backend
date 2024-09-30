package com.ecoswap.ecoswap.user.services.impl;

import com.ecoswap.ecoswap.user.models.dto.AuthenticationResponseDTO;
import com.ecoswap.ecoswap.user.models.dto.LoginDTO;
import com.ecoswap.ecoswap.user.models.dto.RegisterDTO;
import com.ecoswap.ecoswap.user.models.entities.Role;
import com.ecoswap.ecoswap.user.models.entities.User;
import com.ecoswap.ecoswap.user.repositories.UserRepository;
import com.ecoswap.ecoswap.user.services.AuthenticationService;
import com.ecoswap.ecoswap.user.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository iUserRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponseDTO login(LoginDTO login) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());

        authenticationManager.authenticate(authToken);

        User user = iUserRepository.findByEmail(login.getUsername()).get();

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        return new AuthenticationResponseDTO(jwt);
    }

    @Override
    public Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getUsername());
        extraClaims.put("role", user.getRole().name());
        return extraClaims;
    }

    @Override
    public AuthenticationResponseDTO register(RegisterDTO register) {
        User user = new User();
        user.setName(register.getName());
        user.setEmail(register.getEmail());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setAddress(register.getAddress());
        user.setCellphoneNumber(register.getCellphoneNumber());
        user.setRole(Role.valueOf("USER"));

        iUserRepository.save(user);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(register.getEmail(), register.getPassword());

        authenticationManager.authenticate(authToken);

        User userRegister = iUserRepository.findByEmail(register.getEmail()).get();

        String jwt = jwtService.generateToken(userRegister, generateExtraClaims(user));

        return new AuthenticationResponseDTO(jwt);

    }
}
