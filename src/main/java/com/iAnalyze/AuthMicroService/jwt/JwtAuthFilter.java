//package com.iAnalyze.AuthMicroService.jwt;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.iAnalyze.AuthMicroService.dao.UserDao;
//import com.iAnalyze.AuthMicroService.models.User;
//import com.iAnalyze.AuthMicroService.models.UsernameAndPasswordAuthenticationRequest;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Date;
//
//
//public class JwtAuthFilter  extends UsernamePasswordAuthenticationFilter {
//
////    @Autowired
////    private final AuthenticationManager authenticationManager;
//    @Autowired
//    private Environment environment;
//    private UserDao userDao;
//
//    public JwtAuthFilter(AuthenticationManager authenticationManager) {
//        super.setAuthenticationManager(authenticationManager);
//    }
//
//    public JwtAuthFilter(UserDao userDao) {
//        this.userDao = userDao;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//
//        try {
//            UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
//                    .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
////
//
//            return getAuthenticationManager().authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            authenticationRequest.getUsername(),
//                            authenticationRequest.getPassword()
//                    )
//            );
//        } catch(IOException ex) {
//            throw new RuntimeException("Could not attempt authentication");
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request,
//                                            HttpServletResponse response,
//                                            FilterChain chain,
//                                            Authentication authResult) throws IOException, ServletException {
//        String email = ((User) authResult.getPrincipal()).getEmail();
////        String password = ((User) authResult.getPrincipal()).getPassword();
//        User userDetails = userDao.findByEmail(email);
//        String token = Jwts.builder()
//                .signWith(SignatureAlgorithm.HS256, environment.getProperty("token.secret"))
//                .setIssuedAt(new Date())
////
//                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
//                .claim("id", userDetails.getId())
//                .claim("username", userDetails.getUsername())
//                .claim("email", userDetails.getEmail())
//                .compact();
//
//        System.out.println(token);
//
//        response.addHeader("Authorization", environment.getProperty("token.prefix") + token);
//    }
//}
