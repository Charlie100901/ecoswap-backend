package com.ecoswap.ecoswap.user.controllers;

import com.ecoswap.ecoswap.user.models.dto.AuthenticationResponseDTO;
import com.ecoswap.ecoswap.user.models.dto.LoginDTO;
import com.ecoswap.ecoswap.user.models.dto.RegisterDTO;
import com.ecoswap.ecoswap.user.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@Valid @RequestBody LoginDTO login){
        AuthenticationResponseDTO jwt = authenticationService.login(login);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@Valid @RequestBody RegisterDTO registerDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.register(registerDTO));
    }
}
