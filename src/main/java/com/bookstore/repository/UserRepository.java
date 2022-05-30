package com.bookstore.repository;

import com.bookstore.entity.Book;
import com.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
      Optional<User> findByUsername(String username);
      Optional<User> findByBook(Book book);
      @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
      public User findByVerificationCode(String code);
}
