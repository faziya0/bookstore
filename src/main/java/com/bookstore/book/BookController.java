package com.bookstore.book;

import com.bookstore.user.CurrentUser;
import com.bookstore.dto.BookDto;
import com.bookstore.exception.NotFoundException;
import com.bookstore.exception.UnAuthorizeException;
import com.bookstore.user.User;
import com.bookstore.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/api/bookstore/books")
    public List<BookDto> getBooks() {
        return bookService.getBooks().stream().map(book -> modelMapper.map(book,BookDto.class)).collect(Collectors.toList());

    }

    @GetMapping("/api/bookstore/book")
    public Page<BookDto> getBook(@RequestParam String search, @PageableDefault Pageable pageable) {
        return bookService.getBook(search,pageable).map(book -> modelMapper.map(book,BookDto.class));

    }

    @GetMapping("/api/bookstore/books/{publisher}")
    public List<BookDto> getBookByPublisher(@PathVariable String publisher) {
       return bookService.bookByPublisher(publisher).stream().map(book -> modelMapper.map(book,BookDto.class)).collect(Collectors.toList());
    }
    @PostMapping("/api/bookstore/books")
    public ResponseEntity saveBook(@Valid @RequestBody BookSubmitVM bookSubmitVM, @CurrentUser Principal principal){
        Optional<User> user = userService.findUser(principal.getName());
        bookService.saveBook(bookSubmitVM,user.get());
    return ResponseEntity.ok("Book is saved successfully");
    }
    @PutMapping("/api/bookstore/books/{book}")
    public ResponseEntity editBook(@Valid @RequestBody BookSubmitVM bookSubmitVM, @CurrentUser Principal principal,@PathVariable String book){
        Optional<Book> bookInDB = bookService.findBook(book);
        if(bookInDB.isPresent()){
          Optional<User> user1 = userService.findUser(bookInDB.get());
          if(!(user1.get().getUsername().equals(principal.getName()))){
            throw new UnAuthorizeException();
          }
          bookService.editBook(bookInDB.get(),bookSubmitVM);

      }
        else{
            throw new NotFoundException();
        }
        return ResponseEntity.ok("Book is edited");
    }
}