package com.ntnghia.bookmanagement.controller;

import com.ntnghia.bookmanagement.payload.request.LoginRequest;
import com.ntnghia.bookmanagement.payload.request.UserDto;
import com.ntnghia.bookmanagement.payload.response.JwtResponse;
import com.ntnghia.bookmanagement.security.service.AuthService;
import com.ntnghia.bookmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public UserDto registerUserRoleUser(@Valid @RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }
}
