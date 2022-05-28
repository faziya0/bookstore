package com.bookstore.entity;
import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Author {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String fullName;
    @OneToMany(mappedBy ="author" ,cascade = CascadeType.ALL)
    private List<Book> book;

}
