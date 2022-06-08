package com.bookstore.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserDto {
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String username;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",message = "invalid email")
    private String email;
    @NotNull
    private String password;


}
