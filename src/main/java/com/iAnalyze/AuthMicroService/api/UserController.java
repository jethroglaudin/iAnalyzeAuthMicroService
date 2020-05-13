package com.iAnalyze.AuthMicroService.api;

import com.iAnalyze.AuthMicroService.jwt.JwtOps;
import com.iAnalyze.AuthMicroService.models.User;
import com.iAnalyze.AuthMicroService.models.UsernameAndPasswordAuthenticationRequest;
import com.iAnalyze.AuthMicroService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UsernameAndPasswordAuthenticationRequest auth) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return new ResponseEntity<>(jwtOps.generateToken(auth.getEmail(), auth.getPassword()), HttpStatus.OK);
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestBody UsernameAndPasswordAuthenticationRequest auth) throws Exception {
//        User user = userService.validateUser(auth.getEmail(), auth.getPassword());
//        Authentication newUser = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
//        );
//        if (!newUser.isAuthenticated()) {
//            return new ResponseEntity<>("Login unsuccessful", HttpStatus.UNAUTHORIZED);
//        }
//        return new ResponseEntity<>("Login unsuccessful", HttpStatus.OK);
//    }
}
