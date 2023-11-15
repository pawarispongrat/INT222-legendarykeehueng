package sit.int221.announcement.services.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import sit.int221.announcement.dtos.request.UserLogin;
import sit.int221.announcement.dtos.response.RefreshTokenResponse;
import sit.int221.announcement.dtos.response.TokenResponse;
import sit.int221.announcement.exceptions.list.AuthorizedException;
import sit.int221.announcement.exceptions.list.ItemNotFoundException;
import sit.int221.announcement.exceptions.list.UserException;
import sit.int221.announcement.utils.enums.TokenType;
import sit.int221.announcement.utils.security.JwtTokenUtil;

import java.util.Collection;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtTokenUtil jwt;
    @Autowired
    private JwtUserDetailsService service;


    public TokenResponse createToken(UserLogin request) {
        UserDetails details = service.loadUserByUsername(request.getUsername());
        if (details == null) throw new ItemNotFoundException("INVALID_CREDENTIALS");
        authenticate(request.getUsername(),request.getPassword(),details.getAuthorities());
        String username = details.getUsername();
        String token = jwt.generateToken(username, TokenType.ACCESS_TOKEN, getAuthorities(details));
        String refreshToken = jwt.generateToken(username, TokenType.REFRESH_TOKEN);
        return new TokenResponse(token,refreshToken);
    }

    public RefreshTokenResponse createRefreshToken(String refreshToken) {
        if (jwt.isTokenExpired(refreshToken)) throw new AuthorizedException(TokenType.REFRESH_TOKEN.toString(),"Expired Token");
        TokenType type = jwt.getTokenType(refreshToken);
        if (type == TokenType.ACCESS_TOKEN) throw new AuthorizedException(TokenType.REFRESH_TOKEN.toString(),"Token is invalid");
        String username = jwt.getUsernameFromToken(refreshToken);
        UserDetails details = service.loadUserByUsername(username);
        String token = jwt.generateToken(username, TokenType.ACCESS_TOKEN, getAuthorities(details));
        return new RefreshTokenResponse(token);
    }

    private String[] getAuthorities(UserDetails details) {
        return details.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
    }

    private void authenticate(String username, String password, Collection<? extends GrantedAuthority> authority) {
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(username, password, authority));
        } catch (DisabledException e) {
            throw new UserException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new UserException("INVALID_CREDENTIALS");
        }
    }
}
