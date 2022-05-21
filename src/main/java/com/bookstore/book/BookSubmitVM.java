package com.bookstore.book;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
@Data
@Component
public class BookSubmitVM {
    @NotNull
    private String bookName;
    @NotNull
    private String bookType;
    @NotNull
    private String bookSize;
    @NotNull
    private String authorName;
    @NotNull
    private String authorSurname;
}
