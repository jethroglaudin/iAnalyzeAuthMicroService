package com.iAnalyze.AuthMicroService.jwt;

import com.iAnalyze.AuthMicroService.dao.UserDao;
import com.iAnalyze.AuthMicroService.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtOps {

    @Autowired
    private Environment environment;
    @Autowired
    UserDao userDao;


    public Map<String, String> generateToken(String username) {
        return createToken(username);
    }

    private Map<String, String> createToken(String username) {
        User userDetails = userDao.findByUsername(username);
        User user = userDao.findByUsername2(username);
        System.out.println(user.getEmail());
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getId().toString());
        claims.put("email", user.getEmail());
        claims.put("username", userDetails.getUsername());
        String token = environment.getProperty("token.prefix") + Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS256, environment.getProperty("token.secret")).compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(environment.getProperty("token.secret")).parseClaimsJws(token).getBody();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        if(username != null){
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }
        return false;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
