package sit.int221.announcement.utils.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    @Autowired
    private JwtProperties properties;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    public Date getExpirationFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> resolver) {
        Claims claims = getClaimsFromToken(token);
        return resolver.apply(claims);
    }

    public Boolean validateToken(String token, String username) {
        String usernameFromToken = getUsernameFromToken(token);
        return  usernameFromToken.equals(username) && !isTokenExpired(token);
    }
    public String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        return generateToken(claims, user.getUsername());
    }

    private String generateToken(Map<String, Object> claims,String subject) {
        return  Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (properties.getTokenIntervalInHour() * 30 * 60 * 1000))) //30 minutes expired
                .signWith(SignatureAlgorithm.ES512, properties.getSecretKey()).compact();
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationFromToken(token);
        return  expiration.before(new Date());
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(properties.getSecretKey()).parseClaimsJws(token).getBody();
    }
}
