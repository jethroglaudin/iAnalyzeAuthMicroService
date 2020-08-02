package com.iAnalyze.AuthMicroService.api;

import com.iAnalyze.AuthMicroService.exceptions.CustomRuntimeException;
import com.iAnalyze.AuthMicroService.jwt.JwtAuthFilter;
import com.iAnalyze.AuthMicroService.jwt.JwtOps;
import com.iAnalyze.AuthMicroService.models.User;
import com.iAnalyze.AuthMicroService.models.UsernameAndPasswordAuthenticationRequest;
import com.iAnalyze.AuthMicroService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private Environment environment;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;


    UserDetails userDetails;


    @GetMapping("/test")
    public String testAuth() {
        return "Auth Works!";
    }

    @PostMapping("/")
    public ResponseEntity<?> registerUser(@RequestBody @Valid User user) throws CustomRuntimeException {
        try{
            userService.registerUser(null, user);
            Map<String, String> map = new HashMap<>();
            map.put("message", "registered successfully");
            return new ResponseEntity<>(map, HttpStatus.CREATED);
        } catch(Exception ex){
//            throw new CustomRuntimeException("Registration Unsuccessful");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UsernameAndPasswordAuthenticationRequest auth) throws CustomRuntimeException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword())
            );
            return new ResponseEntity<>(jwtOps.generateToken(auth.getUsername()), HttpStatus.OK);
        } catch (Exception ex) {
//            throw new CustomRuntimeException("Invalid email/password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username/password");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(HttpServletRequest request) throws CustomRuntimeException {
        try {
            String token = request.getHeader("Authorization");
//            token = jwtOps.extractUsername(token);

            System.out.println("request " + request.getHeader("Authorization"));
            if(token.startsWith("Bearer ")){
                token = token.substring(7);
                System.out.println(token);
            }

            boolean isValid = jwtAuthFilter.validateRequest(token);
            return ResponseEntity.ok(isValid);
        } catch(CustomRuntimeException ex){
            System.out.println("Error");
        }
        return ResponseEntity.ok(false);
    }
}
