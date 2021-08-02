package com.ntnghia.bookmanagement.payload.response;

import lombok.Data;

@Data
public class JwtResponse {
    private int userId;
    private String token;
    private String type = "Bearer";
    private String email;
    private String role;

    public JwtResponse(int userId, String jwt, String email, String role) {
        this.userId = userId;
        this.token = jwt;
        this.email = email;
        this.role = role;
    }
}
