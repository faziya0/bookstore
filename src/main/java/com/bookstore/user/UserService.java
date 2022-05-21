package com.bookstore.user;

import com.bookstore.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public Boolean isPublisher(String username){
        Optional<User> byUsername = userRepository.findByUsername(username);
        if((!byUsername.isPresent())){
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = byUsername.get().getAuthorities();
        for(GrantedAuthority grantedAuthority:authorities){
            if(grantedAuthority.getAuthority().equals("ROLE_PUBLISHER")){
                return true;
            }
        }
        return false;
    }
    public Optional<User> findUser(String username){
        return userRepository.findByUsername(username);
    }
    public Optional<User> findUser(Book book){
        return userRepository.findByBook(book);
    }
}
