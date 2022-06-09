package com.bookstore.service;

import com.bookstore.entity.User;
import com.bookstore.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class MailService {
    @Qualifier("gmail")
    private final JavaMailSender mailSender;
    @Value("${mail.username}")
    private String fromAddress;
    public void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String subject = "Please verify your registration";
        String content = "Dear "+user.getName()+",<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you.<br>";
        validate(toAddress);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }



    public void validate(String email){

        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();

        } catch (AddressException e) {

            throw new BadRequestException("invalid email");

        }

    }
}

