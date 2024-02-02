package sit.int221.announcement.utils.security.entra;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import sit.int221.announcement.dtos.JwtUser;
import sit.int221.announcement.enumeration.Role;
import sit.int221.announcement.exceptions.list.UserException;
import sit.int221.announcement.models.User;
import sit.int221.announcement.utils.security.PublicKeyExtract;
import sit.int221.announcement.utils.security.jwt.JwtTokenUtil;

import java.io.*;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class EntraTokenUtil {

    @Autowired
    private JwtTokenUtil jwt;
    @Autowired
    private EntraProperties properties;

    private static final String JWK_PROVIDER_URL = "https://login.microsoftonline.com/%tenantId%/discovery/v2.0/keys?appid=%clientId%";

    private static final String LOGIN_MICROSOFT_ONLINE_ISSUER = "https://login.microsoftonline.com/";
    private static final String STS_WINDOWS_ISSUER = "https://sts.windows.net/";
    private static final String STS_CHINA_CLOUD_API_ISSUER = "https://sts.chinacloudapi.cn/";

    public Claims isValidAadToken(String token) throws RuntimeException {
        Claims verifyClaims = verifyToken(token);
        if (verifyClaims == null) return null;
        if (!isAadIssuer(verifyClaims)) return null;
        if (!isValidToken(verifyClaims)) return null;

        return verifyClaims;

    }

    public boolean authenticate(Claims verifyClaims) {
        try {
            if (verifyClaims == null) return false;
            Collection<SimpleGrantedAuthority> authorities = getAuthoritiesByClaims(verifyClaims);
            JwtUser user = new JwtUser( getName(verifyClaims), getEmail(verifyClaims), authorities);
            //VISITOR FOR AZURE
            Authentication authentication = new PreAuthenticatedAuthenticationToken(user,null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (DisabledException | BadCredentialsException e) {
            return false;
        }
        return true;
    }

    public Collection<SimpleGrantedAuthority> getAuthoritiesByClaims(Claims verifyClaims) {
        List<String> roles = verifyClaims.get("roles",List.class); //AZURE NAME
        if (roles == null) return new HashSet<>();
        return roles.stream().map((role) -> new SimpleGrantedAuthority(role.toLowerCase())).collect(Collectors.toSet());
    }
    public String getName(Claims verifyClaims) {
        return verifyClaims.get("name",String.class);
    }
    public String getEmail(Claims verifyClaims) {
        return verifyClaims.get("upn",String.class);
    }

    public boolean isAadIssuer(Claims claims) {
        String issuer = claims.getIssuer();
        if (issuer == null) return false;
        return issuer.startsWith(LOGIN_MICROSOFT_ONLINE_ISSUER)
                || issuer.startsWith(STS_WINDOWS_ISSUER)
                || issuer.startsWith(STS_CHINA_CLOUD_API_ISSUER);
    }

    public boolean isValidToken(Claims claims) {
        return claims != null && !isTokenExpired(claims);
    }

    public Claims verifyToken(String token) throws RuntimeException {
        String jwkProviderUrl = JWK_PROVIDER_URL
                .replace("%tenantId%", properties.getTenantId())
                .replace("%clientId%", properties.getClientId());
        try {

            PublicKey publicKey = new PublicKeyExtract(jwkProviderUrl,token).getX509PublicKey();
            return Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            // Throw an exception for any invalid token
//            throw new RuntimeException("Invalid token: " + e.getMessage());
            throw new RuntimeException("Expired");
        } catch ( CertificateException | IOException e) {
            return null;
        }
    }

    public boolean isTokenExpired(Claims claims) {
        return jwt.isTokenExpiredFromClaims(claims);
    }
}
