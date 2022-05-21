package com.bookstore.auth;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Credential {
    private String username;
    private String password;



}
