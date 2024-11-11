package org.example.authentication_service.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class AuthController {


    @GetMapping("/login")
    public ResponseEntity<Void> login() {
        URI redirectUri = URI.create("http://localhost:4040/companies");
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                .location(redirectUri)
                .build();
    }
}
