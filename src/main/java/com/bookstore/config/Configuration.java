package com.bookstore.config;

import com.bookstore.token.TokenFilter;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@org.springframework.context.annotation.Configuration
public class Configuration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
            }
        });
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/bookstore/books").authenticated().
                            antMatchers(HttpMethod.GET,"/api/bookstore/books/search").authenticated().
                            antMatchers(HttpMethod.GET,"/api/bookstore/books/{publisher}").authenticated().
                            antMatchers(HttpMethod.POST,"/api/bookstore/books").hasAnyAuthority("ROLE_PUBLISHER").
                            antMatchers(HttpMethod.PUT,"/api/bookstore/books/{book}").hasAnyAuthority("ROLE_PUBLISHER").
                            antMatchers(HttpMethod.POST,"/api/bookstore/authenticate").permitAll()
                           .anyRequest().permitAll();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

@Bean
public TokenFilter tokenFilter(){
    return new TokenFilter();
}

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

