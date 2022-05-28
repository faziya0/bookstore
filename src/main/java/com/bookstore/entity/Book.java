package com.bookstore.entity;

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
    @Lob
    private String description;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Author author;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "publisher_id")
    private User user;
}
