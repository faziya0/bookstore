package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.entity.User;
import com.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
   private final UserRepository userRepository;
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
    public Optional<User> getVerificationCode(String code){
       return userRepository.findByVerificationCode(code);
    }
    public void save(User user){
        userRepository.save(user);
    }
}
