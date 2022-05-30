package com.bookstore.controller;

import com.bookstore.entity.User;
import com.bookstore.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;


    @PostMapping("/process_register")
    public String processRegister(User user, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        registerService.register(user, getSiteURL(request));
        return "You have signed up successfully! Please check your email to verify your account";
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
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
