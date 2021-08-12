package com.ntnghia.bookmanagement.security.jwt;

import com.ntnghia.bookmanagement.exception.BadRequestException;
import com.ntnghia.bookmanagement.security.service.impl.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            throw new BadRequestException("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            throw new BadRequestException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            throw new BadRequestException("JWT token is expired");
        } catch (UnsupportedJwtException e) {
            throw new BadRequestException("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("JWT claims string is empty");
        }
    }
}
