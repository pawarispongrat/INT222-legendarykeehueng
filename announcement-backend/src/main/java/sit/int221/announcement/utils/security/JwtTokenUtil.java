package sit.int221.announcement.utils.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import sit.int221.announcement.utils.Utils;
import sit.int221.announcement.utils.enums.Token;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Autowired
    private JwtProperties properties;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Token getTokenType(String token) {
        String type;
        try { type = getClaimFromToken(token, (claims) -> (String) claims.get("type")); }
        catch (ExpiredJwtException e) { type = (String) e.getClaims().get("type"); }
        boolean exists = Utils.existsEnum(Token.class,type);
        return exists ? Token.valueOf(type) : Token.NULL;
    }
    public Date getExpirationFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> resolver) {
        Claims claims = getClaimsFromToken(token);
        return resolver.apply(claims);
    }

    public Boolean validateToken(String token, UserDetails details) {
        String usernameFromToken = getUsernameFromToken(token);
        return  usernameFromToken.equals(details.getUsername()) && isTokenExpired(token);
    }
    public String generateToken(String subject, Token type) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type",type);
        Integer interval = 1;
        if (type == Token.REFRESH_TOKEN) {
            claims.put("username",subject);
            interval = properties.getRefreshTokenIntervalInMinutes();
        } else if (type == Token.ACCESS_TOKEN) {
            interval = properties.getTokenIntervalInMinutes();
        }
        return generateToken(claims,subject,interval); // per 1 minutes
    }

    private String generateToken(Map<String, Object> claims,String subject, int minutes) {
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(minutes, ChronoUnit.MINUTES);

        return  Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration)) //per 1 minutes expired
                .signWith(SignatureAlgorithm.HS512, properties.getSecretKey()).compact();
    }

    public boolean isTokenExpired(String token) {
        try {  return getExpirationFromToken(token).before(new Date()); }
        catch (ExpiredJwtException e) { return true; }
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(properties.getSecretKey()).parseClaimsJws(token).getBody();
    }


}
