package com.ntnghia.bookmanagement.security.service;

import com.ntnghia.bookmanagement.payload.request.LoginRequest;
import com.ntnghia.bookmanagement.payload.response.JwtResponse;

public interface AuthService {
    JwtResponse authUser(LoginRequest loginRequest);
}