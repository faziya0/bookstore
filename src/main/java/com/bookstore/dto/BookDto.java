package com.bookstore.dto;

import com.bookstore.book.Book;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Data
public class BookDto {
    private String name;
    private String type;
    private String size;



}
