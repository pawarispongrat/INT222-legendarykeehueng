package sit.int221.announcement.utils.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import sit.int221.announcement.utils.Utils;
import sit.int221.announcement.utils.enums.Role;
import sit.int221.announcement.utils.enums.TokenType;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Autowired
    private JwtProperties properties;

    public String getUsernameFromToken(String token) {
        if (token == null) return null;
        return getClaimFromToken(token, Claims::getSubject);
    }

    public boolean isEditor(String token,Role... roles) {
        List<String> authorities = getAuthoritiesFromToken(token);
        if (authorities == null) return false;
        List<String> matcher = Arrays.stream(roles).map(Enum::toString).toList();
        return authorities.stream().anyMatch(matcher::contains);
    }

    public  List<String> getAuthoritiesFromToken(String token) {
        if (token == null) return null;
        return getClaimFromToken(token, (claims) -> {
            List<String> claim = claims.get("aut", List.class);
            return claim != null ? new ArrayList<>(claim) : new ArrayList<>();
        });
    }

    public Map<String, Object> getClaims(String token) {
        return getClaimsFromToken(token);
    }
    public TokenType getTokenType(String token) {
        String type = getClaimFromToken(token, (claims) -> (String) claims.get("typ"));
        boolean exists = Utils.existsEnum(TokenType.class,type);
        return exists ? TokenType.valueOf(type) : TokenType.NULL;
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
        return usernameFromToken.equals(details.getUsername()) && !isTokenExpired(token);
    }
    public String generateToken(String subject, TokenType type, String... authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("typ",type);
        if (authorities != null && authorities.length > 0) {
            claims.put("aut", authorities);
        }
        Integer interval = properties.getIntervalInMinutes().get(type);
        if (interval == null) interval = 1;

        return generateToken(claims,subject,interval);
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
        try { return getExpirationFromToken(token).before(new Date()); }
        catch (ExpiredJwtException e) { return true; }
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(properties.getSecretKey()).parseClaimsJws(token).getBody();
    }


}
