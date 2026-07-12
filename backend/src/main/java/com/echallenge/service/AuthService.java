package com.echallenge.service;

import com.echallenge.dto.AuthResponse;
import com.echallenge.dto.LoginRequest;
import com.echallenge.dto.RegisterRequest;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    AuthResponse register(RegisterRequest request);
}
