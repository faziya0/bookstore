package com.bookstore.controller;

import com.bookstore.entity.Book;
import com.bookstore.service.BookService;
import com.bookstore.shared.CurrentUser;
import com.bookstore.dto.BookDto;
import com.bookstore.exception.NotFoundException;
import com.bookstore.entity.User;
import com.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/api/bookstore/books")
    public List<BookDto> getBooks() {
        return bookService.getBooks().stream().map(book -> modelMapper.map(book,BookDto.class)).collect(Collectors.toList());

    }
    @GetMapping("api/bookstore/books/{book}")
    public ResponseEntity<BookDto> getBook(@PathVariable String book){
        Optional<Book> bookInDB = bookService.findBook(book);
        if (!(bookInDB.isPresent())){
            throw new NotFoundException("no such book found");
        }
        BookDto bookDto = modelMapper.map(bookInDB.get(), BookDto.class);
        return ResponseEntity.ok(bookDto);

    }

    @GetMapping("/api/bookstore/book")
    public Page<BookDto> getSearchBook(@RequestParam String search, @PageableDefault Pageable pageable) {
        return bookService.getBook(search,pageable).map(book -> modelMapper.map(book,BookDto.class));

    }

    @GetMapping("/api/bookstore/books/byPublisher/{publisher}")
    public List<BookDto> getBookByPublisher(@PathVariable String publisher) {
       return bookService.bookByPublisher(publisher).stream().map(book -> modelMapper.map(book,BookDto.class)).collect(Collectors.toList());
    }
    @PostMapping("/api/bookstore/books")
    public ResponseEntity<String> saveBook(@Valid @RequestBody BookDto bookDto, @CurrentUser Principal principal){
        Optional<User> user = userService.findUser(principal.getName());
        bookService.saveBook(bookDto,user.get());
    return ResponseEntity.ok("Book is saved successfully");
    }
    @PreAuthorize("@securityService.isAllowedEdit(#book,principal)")
    @PutMapping("/api/bookstore/books/{book}")
    public ResponseEntity<String> editBook(@Valid @RequestBody BookDto bookDto,@PathVariable String book){
        Optional<Book> bookInDB = bookService.findBook(book);
          bookService.editBook(bookInDB.get(),bookDto);
          return ResponseEntity.ok("Book is edited successfully");

    }
}