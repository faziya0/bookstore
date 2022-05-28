package com.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto {
    @NotNull
    private String name;
    @NotNull
    private String type;
    private String size;
    private String description;
    @NotNull
    private AuthorDto author;



}
