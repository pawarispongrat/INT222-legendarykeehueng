package sit.int221.announcement.services;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import sit.int221.announcement.dtos.response.TokenResponse;
import sit.int221.announcement.enumeration.TokenType;
import sit.int221.announcement.exceptions.list.ItemNotFoundException;
import sit.int221.announcement.models.User;
import sit.int221.announcement.repositories.UserRepository;
import sit.int221.announcement.utils.security.entra.EntraTokenUtil;
import sit.int221.announcement.utils.security.jwt.JwtTokenUtil;

@Service
public class EntraService {

    @Autowired
    private EntraTokenUtil entra;
    @Autowired
    private JwtTokenUtil jwt;
    @Autowired
    private UserRepository users;

    public TokenResponse createTokenByEntraToken(String entraToken) {
        Claims claims = entra.isValidAadToken(entraToken);
        if (claims == null) throw new ItemNotFoundException("INVALID_CREDENTIALS");
        User user = users.findByEmail(entra.getEmail(claims)).orElse(null);
        if (user == null) {
            entra.authenticateAad(entraToken); //auth by aad (Visitor)
            return new TokenResponse(entraToken, null);
        }
        entra.authenticateByUser(user); //auth by my own db

        String token = jwt.generateTokenWithClaims(user.getUsername(), TokenType.ACCESS_TOKEN, jwt.getAccessTokenClaims(user));
        String refreshToken = jwt.generateToken(user.getUsername(), TokenType.REFRESH_TOKEN);
        return new TokenResponse(token,refreshToken);
    }
}
