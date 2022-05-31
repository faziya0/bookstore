package com.bookstore.auth;


import com.bookstore.dto.Credential;
import com.bookstore.exception.UnAuthorizeException;
import com.bookstore.token.JwtTokenUtil;
import com.bookstore.entity.User;
import com.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

   private final JwtTokenUtil jwtTokenUtil;
   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;
    public String auth(Credential credential){
        Optional<User> userDB = userRepository.findByUsername(credential.getUsername());
        if(!(userDB.isPresent())){
            throw  new UnAuthorizeException();
        }
        else if(!(passwordEncoder.matches(credential.getPassword(), userDB.get().getPassword()) && !(userDB.get().isEnabled()))) {
            throw  new UnAuthorizeException();
        }

        return jwtTokenUtil.createToken(userDB.get());

    }
}
