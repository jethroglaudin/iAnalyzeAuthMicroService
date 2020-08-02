package com.iAnalyze.AuthMicroService.jwt;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;



@Component
public class JwtAuthFilter  {

    @Autowired
    Environment environment;

    public boolean validateRequest(String token) {
        try {
            Jwts.parser().setSigningKey(environment.getProperty("token.secret")).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid jwt signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid jwt token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired jwt token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT Token");
        } catch (IllegalArgumentException ex) {
            System.out.println("Empty Jwt string");
        }
        return false;
    }

}
