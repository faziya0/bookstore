package com.bookstore.token;

import com.bookstore.error.ErrorResponse;
import com.bookstore.exception.UnAuthorizeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;



public class TokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      try {
          String authorization = request.getHeader("Authorization");
          if (authorization != null) {
              String token = authorization.substring(7);
              if (jwtTokenUtil.validateToken(token)) {
                  String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token);
                  List<Object> authorities = jwtTokenUtil.getAuthorities(token);

                  Principal principal = new Principal() {
                      @Override
                      public String getName() {
                          return usernameFromToken;
                      }
                  };
                  List<GrantedAuthority> authorityList = new ArrayList<>();
                  for (Object authority : authorities) {
                      authorityList.add(new SimpleGrantedAuthority(authority.toString()));

                  }


                  UsernamePasswordAuthenticationToken auth =
                          new UsernamePasswordAuthenticationToken(principal, null, authorityList);
                  SecurityContextHolder.getContext().setAuthentication(auth);
              }


          }

          filterChain.doFilter(request, response);
      }
      catch(JwtException ex){
          HttpServletResponse res = (HttpServletResponse) response;
          res.setStatus(HttpStatus.UNAUTHORIZED.value());
          ErrorResponse error = new ErrorResponse();
          error.setStatus(HttpStatus.UNAUTHORIZED.value());
          error.setMessage(ex.getMessage());
          res.setContentType("application/json");
          response.getWriter().write(convertObjectToJson(error));
      }
    }
    public String convertObjectToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        return  mapper.writeValueAsString(obj);
    }
}