package com.bookstore.book;

import com.bookstore.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Integer> {


@Query("select b from Book b " +
        "where concat" +
        "(lower(b.name), lower(b.type),lower(b.author.name),lower(b.author.surname))" +
        " like lower(concat('%',?1,'%') ) ")


Page<Book> findBook(String searchElement,Pageable page);
   Optional<List<Book>>  findByUser(User user);
   Optional<Book> findByName(String name);
}



