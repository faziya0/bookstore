package com.bookstore.author;

import com.bookstore.book.Book;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Author {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    @OneToMany(mappedBy ="author" ,cascade = CascadeType.ALL)
    private List<Book> book;

}
