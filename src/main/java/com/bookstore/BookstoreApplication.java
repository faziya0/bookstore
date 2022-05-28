package com.bookstore;

import com.bookstore.role.Role;
import com.bookstore.entity.User;
import com.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@RequiredArgsConstructor

public class BookstoreApplication {

private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;


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


	}



	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

}
