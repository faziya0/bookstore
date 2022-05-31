package com.bookstore.register;

import com.bookstore.dto.UserDto;
import com.bookstore.register.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;


    @PostMapping("/register")
    public String processRegister(@Valid @RequestBody UserDto userDto, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        registerService.register(userDto, registerService.getSiteURL(request));
        return "You have signed up successfully! Please check your email to verify your account";
    }


    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (registerService.verify(code)) {
            return "Congratulations, your account has been verified";
        } else {
            return "Sorry, we could not verify account. It maybe already verified,\n" +
                    "        or verification code is incorrect";
        }
    }
}
