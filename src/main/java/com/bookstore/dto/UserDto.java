package com.bookstore.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;


}
