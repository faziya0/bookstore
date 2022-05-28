package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.entity.User;
import com.bookstore.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service(value = "securityService")
@RequiredArgsConstructor
public class SecurityService {
    private final BookService bookService;
    private final UserService userService;
    public boolean isAllowedEdit(String book, Principal principal){
        Optional<Book> bookInDB = bookService.findBook(book);
        if(bookInDB.isPresent()){
            Optional<User> user1 = userService.findUser(bookInDB.get());
            if(user1.get().getUsername().equals(principal.getName())){
                return true;
    }
}
        else{
            throw new NotFoundException("no such book found");
        }
    return false;
    }}
