package com.mfturkcan.addressbooksystemrest.controllers;

import com.mfturkcan.addressbooksystemrest.dtos.AuthenticationRequest;
import com.mfturkcan.addressbooksystemrest.dtos.AuthenticationResponse;
import com.mfturkcan.addressbooksystemrest.models.BookUser;
import com.mfturkcan.addressbooksystemrest.models.BookUserDetail;
import com.mfturkcan.addressbooksystemrest.repositories.BookUserRepository;
import com.mfturkcan.addressbooksystemrest.services.AuthenticationService;
import com.mfturkcan.addressbooksystemrest.services.BookUserDetailService;
import com.mfturkcan.addressbooksystemrest.services.BookUserService;
import com.mfturkcan.addressbooksystemrest.utils.JwtUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final BookUserService bookUserService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(BookUserService bookUserService, AuthenticationService authenticationService) {
        this.bookUserService = bookUserService;
        this.authenticationService = authenticationService;
    }

    @GetMapping(path = "/user")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String jwt){
        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
            var result = authenticationService.getUser(jwt);
            return ResponseEntity.status(result.getStatus()).body(result);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No token found!");
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        var result = authenticationService.authenticateUser(authenticationRequest);

        return ResponseEntity.status(result.getStatus()).body(authenticationService.authenticateUser(authenticationRequest));
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody BookUser bookUser) throws Exception{
        boolean userExists = bookUserService.isUserExists(bookUser.getUsername());

        if(!userExists){
            bookUserService.addUser(bookUser);

            var result = authenticationService.authenticateUser(
                    new AuthenticationRequest(bookUser.getUsername(), bookUser.getPassword()));

            return ResponseEntity.status(result.getStatus()).body(result);
        }
        var response = new AuthenticationResponse();
        response.setMessage("User already exists with username : "+ bookUser.getUsername());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}