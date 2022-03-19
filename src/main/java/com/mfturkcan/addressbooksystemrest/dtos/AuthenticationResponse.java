package com.mfturkcan.addressbooksystemrest.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    String jwt;
    Object message;
    HttpStatus status;
    private String username;
    private String role;
}