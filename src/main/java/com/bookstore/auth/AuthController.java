package com.bookstore.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;
    @PostMapping("/api/bookstore/authenticate")
    public ResponseEntity<AuthResponse> auth(@RequestBody Credential credential){
        String token = authService.auth(credential);
        AuthResponse response = new AuthResponse(token);
        return ResponseEntity.ok(response);
    }



}
