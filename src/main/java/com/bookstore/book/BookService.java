package com.bookstore.book;

import com.bookstore.author.Author;
import com.bookstore.author.AuthorService;
import com.bookstore.exception.NotFoundException;
import com.bookstore.exception.UnAuthorizeException;
import com.bookstore.user.User;
import com.bookstore.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserService userService;
    @Autowired
    AuthorService authorService;
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
    public void saveBook(BookSubmitVM bookSubmitVM,User user){
    Book book = new Book();
    book.setName(bookSubmitVM.getBookName());
    book.setType(bookSubmitVM.getBookType());
    book.setSize(bookSubmitVM.getBookSize());
    Optional<Author> authorInDB = authorService.findAuthor(bookSubmitVM.getAuthorName(), bookSubmitVM.getAuthorSurname());
    if(!(authorInDB.isPresent())){
        Author author = new Author();
        author.setName(bookSubmitVM.getAuthorName());
        author.setSurname(bookSubmitVM.getAuthorSurname());
        authorService.saveAuthor(author);
        book.setAuthor(author);
    }
    else{
        book.setAuthor(authorInDB.get());
    }
    book.setUser(user);
   bookRepository.save(book);

    }
    public Optional<Book> findBook(String name){
        return bookRepository.findByName(name);
    }
    public void editBook(Book book,BookSubmitVM bookSubmitVM){
        book.setName(bookSubmitVM.getBookName());
        book.setType(bookSubmitVM.getBookType());
        book.setSize(bookSubmitVM.getBookSize());
        Author author = book.getAuthor();
        author.setName(bookSubmitVM.getAuthorName());
        author.setSurname(bookSubmitVM.getAuthorSurname());
        bookRepository.save(book);
    }
}
