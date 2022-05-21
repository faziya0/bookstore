package com.bookstore.user;

import com.bookstore.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    public Optional<User> findByUsername(String username);
    public Optional<User> findByBook(Book book);
}
