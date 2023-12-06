package sit.int221.announcement.utils.security.entra;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import sit.int221.announcement.enumeration.Role;
import sit.int221.announcement.models.User;
import sit.int221.announcement.utils.security.PublicKeyExtract;
import sit.int221.announcement.utils.security.jwt.JwtTokenUtil;

import java.io.*;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.util.*;


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

    public Claims authenticateAad(String token) throws RuntimeException {
        Claims verifyClaims = isValidAadToken(token);
        if (verifyClaims == null) return null;
//      Ref:  AadAuthenticationFilter from azure active directory
        this.authenticateByUser(verifyClaims);

        return verifyClaims;

    }
    public Claims isValidAadToken(String token) throws RuntimeException {
        Claims verifyClaims = verifyToken(token);
        if (verifyClaims == null) return null;
        if (!isAadIssuer(verifyClaims)) return null;
        if (!isValidToken(verifyClaims)) return null;
//      Ref:  AadAuthenticationFilter from azure active directory

        return verifyClaims;

    }


    public void authenticateByUser(User user) {
        Collection<SimpleGrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().toString()));
        Authentication authentication = new PreAuthenticatedAuthenticationToken(user,null, roles);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void authenticateByUser(Claims verifyClaims) {
        String email = verifyClaims.get("upn", String.class); //AZURE EMAIL
        String name = verifyClaims.get("name",String.class); //AZURE NAME
        EntraUser user = new EntraUser(email, name);
        //VISITOR FOR AZURE
        Collection<SimpleGrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(Role.visitor.toString()));
        Authentication authentication = new PreAuthenticatedAuthenticationToken(user,null, roles);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public String getEmail(Claims claims) {
        return claims.get("email",String.class);
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
