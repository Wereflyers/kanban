package ru.kanban.main.configuration.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.kanban.main.exception.AccessDenialException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

/**
 * The type Jwt token provider.
 */
@Component
@PropertySource(value = {"classpath:application.properties"})
public class JwtTokenProvider {
    private final UserDetailsService service;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.header}")
    private String authHeader;
    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    /**
     * Instantiates a new Jwt token provider.
     *
     * @param userDetailsService the user details service
     */
    public JwtTokenProvider(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.service = userDetailsService;
    }

    /**
     * Init.
     */
    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate token string.
     *
     * @param username the username
     * @return the string
     */
    public String generateToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime.toMillis());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * Validate token boolean.
     *
     * @param token the token
     * @return the boolean
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new AccessDenialException(HttpStatus.UNAUTHORIZED.toString());
        }
    }

    /**
     * Gets authentication.
     *
     * @param token the token
     * @return the authentication
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = service.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Gets username.
     *
     * @param token the token
     * @return the username
     */
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Resolve token string.
     *
     * @param request the request
     * @return the string
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authHeader);
    }

}
