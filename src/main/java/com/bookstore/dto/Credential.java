package com.bookstore.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Data
@Component
public class Credential {
    @NotNull
    private String username;
    @NotNull
    private String password;



}
