package com.iAnalyze.AuthMicroService.api;

import com.iAnalyze.AuthMicroService.exceptions.CustomRuntimeException;
import com.iAnalyze.AuthMicroService.jwt.JwtOps;
import com.iAnalyze.AuthMicroService.models.User;
import com.iAnalyze.AuthMicroService.models.EmailAndPasswordAuthenticationRequest;
import com.iAnalyze.AuthMicroService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtOps jwtOps;


    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody @Valid User user) {
        userService.registerUser(null, user);
        Map<String, String> map = new HashMap<>();
        map.put("message", "registered successfully");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody EmailAndPasswordAuthenticationRequest auth) throws CustomRuntimeException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword())
            );
        } catch (Exception ex) {
            throw new CustomRuntimeException("Invalid email/password");
        }
        return new ResponseEntity<>(jwtOps.generateToken(auth.getEmail(), auth.getPassword()), HttpStatus.OK);
    }
}
