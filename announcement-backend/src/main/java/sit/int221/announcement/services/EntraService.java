package sit.int221.announcement.services;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import sit.int221.announcement.dtos.JwtUser;
import sit.int221.announcement.dtos.response.TokenResponse;
import sit.int221.announcement.enumeration.TokenType;
import sit.int221.announcement.exceptions.list.ItemNotFoundException;
import sit.int221.announcement.models.User;
import sit.int221.announcement.repositories.UserRepository;
import sit.int221.announcement.services.authentication.JwtUserDetailsService;
import sit.int221.announcement.utils.security.entra.EntraTokenUtil;
import sit.int221.announcement.utils.security.jwt.JwtTokenUtil;

@Service
public class EntraService {

    @Autowired
    private EntraTokenUtil entra;
    @Autowired
    private JwtTokenUtil jwt;
    @Autowired
    private JwtUserDetailsService jwtUser;
    @Autowired
    private UserRepository users;

    public TokenResponse createTokenByEntraToken(String entraToken) {
        Claims claims = entra.isValidAadToken(entraToken);
        if (claims == null) throw new ItemNotFoundException("INVALID_CREDENTIALS");
        JwtUser user = jwtUser.loadUserByEmail(entra.getEmail(claims));
        if (user == null) {
            entra.authenticate(claims);
            return new TokenResponse(entraToken, null);
        } else {
            jwtUser.authenticate(user,null);
            String token = jwt.generateTokenWithClaims(user.getUsername(), TokenType.ACCESS_TOKEN, jwt.getAccessTokenClaims(user));
            String refreshToken = jwt.generateToken(user.getUsername(), TokenType.REFRESH_TOKEN);
            return new TokenResponse(token,refreshToken);
        }
    }
}
