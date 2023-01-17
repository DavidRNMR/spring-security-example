package com.security.security.jwt;


import com.security.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${bezkoder.app.jwtSecret}")
    private String jwtSecret;

    @Value("${bezkoder.app.jwtExpirationMs")
    private int jwtExpirationMs;

    public String generateJwtToken (Authentication authentication){

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }
    public String getuserNameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken (String authToken){

        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }
        catch (SignatureException e){
            log.error("Invalid JWT signature: {}", e.getMessage());
        }
        catch (MalformedJwtException e){
            log.error("Invalid JWT {}",e.getMessage());
        }
        catch (ExpiredJwtException e){
            log.error("JWT is expired {}",e.getMessage());
        }
        catch (UnsupportedJwtException e){
            log.error("JWT is unssupported:{}",e.getMessage());
        }
        catch (IllegalArgumentException e){
            log.error("JTW is empty {}",e.getMessage());
        }
        return false;
    }
}
