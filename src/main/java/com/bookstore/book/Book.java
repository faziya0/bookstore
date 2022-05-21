package com.bookstore.book;

import com.bookstore.author.Author;
import com.bookstore.user.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String type;
    private String size;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Author author;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "publisher_id")
    private User user;
}
