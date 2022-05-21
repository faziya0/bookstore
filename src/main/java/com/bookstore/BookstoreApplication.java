package com.bookstore;

import com.bookstore.author.Author;
import com.bookstore.author.AuthorRepository;
import com.bookstore.book.Book;
import com.bookstore.book.BookRepository;
import com.bookstore.role.Role;
import com.bookstore.user.User;
import com.bookstore.user.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BookstoreApplication {
@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	AuthorRepository authorRepository;

	@PostConstruct
	public void init(){
		User user=new User();
		user.setName("Name1");
		user.setSurname("Surname1");
		user.setUsername("username1");
		user.setEmail("name1@gmail.com");
		String encode = passwordEncoder.encode("12345");
		user.setPassword(encode);
		List<Role> user1roles = Arrays.asList(Role.ROLE_USER);
		user.setRole(user1roles);
		userRepository.save(user);
		User user2=new User();
		user2.setName("Name2");
		user2.setSurname("Surname2");
		user2.setUsername("username2");
		user2.setEmail("name2@gmail.com");
		String encode2 = passwordEncoder.encode("12345");
		user2.setPassword(encode2);
		List<Role> user2roles = Arrays.asList(Role.ROLE_PUBLISHER);
		user2.setRole(user2roles);
		userRepository.save(user2);
		Author author = new Author();
		author.setName("Fyodor");
		author.setSurname("Dostoevsky");
		authorRepository.save(author);
		Author author2 = new Author();
		author2.setName("Ismayil");
		author2.setSurname("Sixli");
		authorRepository.save(author2);
		Book book = new Book();
		book.setName("Deli Kur");
		book.setType("roman");
		book.setSize("400");
		book.setUser(user2);
		book.setAuthor(author2);
		bookRepository.save(book);
		Book book2 = new Book();
		book2.setName("Idiot");
		book2.setType("roman");
		book2.setSize("1000");
		book2.setUser(user2);
		book2.setAuthor(author);
		bookRepository.save(book2);


	}



	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

}
