package com.bookstore.dto;

import com.bookstore.constraint.email.UniqueEmail;
import com.bookstore.constraint.username.UniqueUserName;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    @UniqueUserName
    private String username;
    @NotNull
    @UniqueEmail
    private String email;
    @NotNull
    private String password;


}
