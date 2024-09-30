package com.ecoswap.ecoswap.user.services;

import com.ecoswap.ecoswap.user.models.dto.AuthenticationResponseDTO;
import com.ecoswap.ecoswap.user.models.dto.LoginDTO;
import com.ecoswap.ecoswap.user.models.dto.RegisterDTO;
import com.ecoswap.ecoswap.user.models.entities.User;

import java.util.Map;

public interface AuthenticationService {

    AuthenticationResponseDTO login(LoginDTO login);
    Map<String, Object> generateExtraClaims(User user);
    AuthenticationResponseDTO register(RegisterDTO register);
}
