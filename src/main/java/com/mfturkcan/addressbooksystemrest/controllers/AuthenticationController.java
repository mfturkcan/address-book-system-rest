package com.mfturkcan.addressbooksystemrest.controllers;

import com.mfturkcan.addressbooksystemrest.dtos.AuthenticationRequest;
import com.mfturkcan.addressbooksystemrest.dtos.AuthenticationResponse;
import com.mfturkcan.addressbooksystemrest.dtos.ControllerResponse;
import com.mfturkcan.addressbooksystemrest.models.BookUser;
import com.mfturkcan.addressbooksystemrest.services.AuthenticationService;
import com.mfturkcan.addressbooksystemrest.services.BookUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

    @GetMapping
    public String test(){
        return "Test";
    }

    @GetMapping(path = "/user")
    public ResponseEntity<?> getUser(@RequestHeader(value = "Authorization", required = false) String jwt){
        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
            var result = authenticationService.getUser(jwt);
            return ResponseEntity.status(result.getStatus()).body(result);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ControllerResponse(HttpStatus.UNAUTHORIZED.toString(), "No token found!"));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        var result = authenticationService.authenticateUser(authenticationRequest);

        return ResponseEntity.status(result.getStatus()).body(authenticationService.authenticateUser(authenticationRequest));
    }

    @Transactional
    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody BookUser bookUser) throws Exception{
        boolean userExists = bookUserService.isUserExists(bookUser.getUsername());

        if(!userExists){
            bookUserService.addBookUser(bookUser);

            var result = authenticationService.authenticateUser(
                    new AuthenticationRequest(bookUser.getUsername(), bookUser.getPassword()));

            return ResponseEntity.status(result.getStatus()).body(result);
        }
        var response = new AuthenticationResponse();
        response.setMessage("User already exists with username : "+ bookUser.getUsername());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}