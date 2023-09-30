package sit.int221.announcement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import sit.int221.announcement.dtos.request.UserLoginDTO;
import sit.int221.announcement.dtos.response.RefreshTokenResponse;
import sit.int221.announcement.dtos.response.TokenResponse;
import sit.int221.announcement.exceptions.list.AuthorizedException;
import sit.int221.announcement.exceptions.list.ItemNotFoundException;
import sit.int221.announcement.exceptions.list.UserException;
import sit.int221.announcement.utils.enums.Token;
import sit.int221.announcement.utils.security.JwtTokenUtil;
import sit.int221.announcement.utils.security.JwtUserDetailsService;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtTokenUtil jwt;
    @Autowired
    private JwtUserDetailsService service;


    public TokenResponse createToken(UserLoginDTO request) {
        UserDetails details = service.loadUserByUsername(request.getUsername());
        if (details == null) throw new ItemNotFoundException("username");
        authenticate(request.getUsername(),request.getPassword());
        String username = details.getUsername();
        String token = jwt.generateToken(username, Token.ACCESS_TOKEN);
        String refreshToken = jwt.generateToken(username, Token.REFRESH_TOKEN);
        return new TokenResponse(token,refreshToken);
    }

    public RefreshTokenResponse createRefreshToken(String refreshToken) {
        if (jwt.isTokenExpired(refreshToken)) throw new AuthorizedException("refreshToken","Expired");
        Token type = jwt.getTokenType(refreshToken);
        if (type == Token.ACCESS_TOKEN) throw new AuthorizedException("refreshToken");
        String username = jwt.getUsernameFromToken(refreshToken);
        String token = jwt.generateToken(username,Token.ACCESS_TOKEN);
        return new RefreshTokenResponse(token);
    }


    private void authenticate(String username,String password) {
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        } catch (DisabledException e) {
            throw new UserException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new UserException("INVALID_CREDENTIALS");
        }
    }
}
