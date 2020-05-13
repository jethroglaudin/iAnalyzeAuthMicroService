package com.iAnalyze.AuthMicroService.jwt;

import com.iAnalyze.AuthMicroService.dao.UserDao;
import com.iAnalyze.AuthMicroService.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtOps {

    @Autowired
    private Environment environment;
    @Autowired
    UserDao userDao;

    public String generateToken(String email, String password) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(email, password);
    }

    private String createToken(String email, String username) {
        User userDetails = userDao.findByEmail(email);
//        String token = Jwts.builder()
//                .signWith(SignatureAlgorithm.HS256, environment.getProperty("token.secret"))
//                .setIssuedAt(new Date())
////
//                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
//                .claim("id", userDetails.getId())
//                .claim("username", userDetails.getUsername())
//                .claim("email", userDetails.getEmail())
//                .compact();

        return Jwts.builder()
                .claim("id", userDetails.getId())
                .claim("username", userDetails.getUsername())
                .claim("email", userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS256, environment.getProperty("token.secret")).compact();
    }
}
