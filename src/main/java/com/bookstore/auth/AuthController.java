package com.bookstore.auth;

import com.bookstore.dto.Credential;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {
   private final AuthService authService;
    @PostMapping("/api/bookstore/authenticate")
    public ResponseEntity<AuthResponse> auth(@Valid @RequestBody Credential credential){
        String token = authService.auth(credential);
        AuthResponse response = new AuthResponse(token);
        return ResponseEntity.ok(response);
    }



}
