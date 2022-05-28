package com.bookstore.service;

import com.bookstore.dto.BookDto;
import com.bookstore.entity.Author;
import com.bookstore.repository.BookRepository;
import com.bookstore.entity.Book;
import com.bookstore.exception.NotFoundException;
import com.bookstore.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserService userService;
    private final AuthorService authorService;
    private final ModelMapper modelMapper;
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }
    public Page<Book> getBook(String searchElement,Pageable page){
       return bookRepository.findBook(searchElement,page);
    }
    public List<Book> bookByPublisher(String username){
        if(userService.isPublisher(username)){
            Optional<User> user = userService.findUser(username);
            Optional<List<Book>> bookByPublisher = bookRepository.findByUser(user.get());
            if((!bookByPublisher.isPresent())){
                throw new NotFoundException("no such book found");
            }
            return bookByPublisher.get();
        }
        else{
            throw new NotFoundException("no such publisher found");
        }
    }
    public void saveBook(BookDto bookDto, User user){
    Optional<Author> authorInDB = authorService.findAuthor(bookDto.getAuthor().getFullName());
        Book book = modelMapper.map(bookDto, Book.class);
        if(authorInDB.isPresent()){
        book.setAuthor(authorInDB.get());
    }
    book.setUser(user);
    bookRepository.save(book);

    }
    public Optional<Book> findBook(String name){
        return bookRepository.findByName(name);
    }
    public void editBook(Book book,BookDto bookDto){
        modelMapper.map(bookDto,book);
        Optional<Author> authorInDB = authorService.findAuthor(bookDto.getAuthor().getFullName());
        if(authorInDB.isPresent()){
            book.setAuthor(authorInDB.get());
        }
        bookRepository.save(book);
    }
}
