package com.bookstore.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;
    public Optional<Author> findAuthor(String name, String surname){
        return authorRepository.findByNameAndSurname(name,surname);
    }
    public void saveAuthor(Author author){
        authorRepository.save(author);
    }
}
