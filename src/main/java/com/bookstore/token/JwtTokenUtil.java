package com.bookstore.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secretKey;
    public String generateToken(String subject, Map<String,Object> claims){
       return Jwts.builder().addClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).
        setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10)).signWith(SignatureAlgorithm.HS256,secretKey).compact();
    }
    public String createToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        Set<String> userRoles = new HashSet<>();
        for(GrantedAuthority authority: userDetails.getAuthorities()){
            userRoles.add(authority.getAuthority());
        }
        claims.put("roles",userRoles.toArray());
        return generateToken(userDetails.getUsername(), claims);
        }
    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        Claims allClaimsFromToken = getAllClaimsFromToken(token);
      return  allClaimsFromToken.getSubject();

    }
    public Date getExpirationDateFromToken(String token) {
        Claims allClaimsFromToken = getAllClaimsFromToken(token);
        return allClaimsFromToken.getExpiration();
    }
    private Boolean isTokenExpired(String token) {
         Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public Boolean validateToken(String token) {
        String usernameFromToken = getUsernameFromToken(token);
        if(usernameFromToken!=null && !isTokenExpired(token)){
            return true;
        }
        return false;
    }
    public List<Object> getAuthorities(String token){
        Claims allClaimsFromToken = getAllClaimsFromToken(token);
            List<Object> authorities = (List<Object>)allClaimsFromToken.get("roles", Object.class);
            return authorities;
    }
        }
