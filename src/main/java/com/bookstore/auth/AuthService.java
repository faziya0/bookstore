package com.bookstore.auth;


import com.bookstore.exception.UnAuthorizeException;
import com.bookstore.token.JwtTokenUtil;
import com.bookstore.user.User;
import com.bookstore.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public String auth(Credential credential){
        Optional<User> userDB = userRepository.findByUsername(credential.getUsername());
        if(!(userDB.isPresent())){
            throw  new UnAuthorizeException();
        }
        else if(!(passwordEncoder.matches(credential.getPassword(), userDB.get().getPassword()))) {
            throw  new UnAuthorizeException();
        }
        return jwtTokenUtil.createToken(userDB.get());

    }
}
