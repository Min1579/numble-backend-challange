package me.min.karrotmarket.security.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import me.min.karrotmarket.security.CurrentUser;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenProvider {
    @Value("${tokenSecret}")
    private String tokenSecret;

    @Value("${tokenExpirationMsec}")
    private long tokenExpirationMsec;

    public String createToken(final Authentication authentication) {
        CurrentUser authUser = (CurrentUser) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(Long.toString(authUser.getId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + tokenExpirationMsec))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    public Long getUserIdFromToken(final String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(final String token) {
        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
