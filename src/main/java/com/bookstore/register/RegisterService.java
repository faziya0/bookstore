package com.bookstore.register;

import com.bookstore.dto.UserDto;
import com.bookstore.entity.User;
import com.bookstore.role.Role;
import com.bookstore.service.MailService;
import com.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Qualifier("gmail")
    private final MailService mailService;
    private final ModelMapper modelMapper;


    public void register(UserDto userDto, String siteURL) throws MessagingException, UnsupportedEncodingException {
        User user = modelMapper.map(userDto, User.class);
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Arrays.asList(Role.ROLE_USER));
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        userService.save(user);
        mailService.sendVerificationEmail(user, siteURL);
    }
    public boolean verify(String verificationCode) {
        Optional<User> user = userService.getVerificationCode(verificationCode);

        if (!(user.isPresent()) || user.get().isEnabled()) {
            return false;
        } else {
            user.get().setVerificationCode(null);
            user.get().setEnabled(true);
            userService.save(user.get());

            return true;
        }

    }
    public String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }


}
