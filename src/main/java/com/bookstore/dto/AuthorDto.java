package com.bookstore.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthorDto {
    @NotNull
    private String fullName;
}
