package com.bookstore.author;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
    public Optional<Author> findByNameAndSurname(String name, String surname);
}
