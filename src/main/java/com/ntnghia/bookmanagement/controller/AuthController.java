package com.ntnghia.bookmanagement.controller;

import com.ntnghia.bookmanagement.entity.User;
import com.ntnghia.bookmanagement.payload.request.LoginRequest;
import com.ntnghia.bookmanagement.payload.response.JwtResponse;
import com.ntnghia.bookmanagement.security.service.AuthService;
import com.ntnghia.bookmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authUser(loginRequest);
    }

    @PostMapping("/register")
    public User registerUserRoleUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }
}
