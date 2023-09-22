package sit.int221.announcement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import sit.int221.announcement.dtos.request.UserLoginDTO;
import sit.int221.announcement.dtos.response.TokenResponse;
import sit.int221.announcement.exceptions.list.UserException;
import sit.int221.announcement.utils.security.JwtTokenUtil;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtTokenUtil jwt;
    @Autowired
    private UserDetailsService service;

    public TokenResponse createToken(UserLoginDTO request) {
        authenticate(request.getUsername(),request.getPassword());
        UserDetails details = service.loadUserByUsername(request.getUsername());
        String token = jwt.generateToken(details);
        return new TokenResponse(token,token);
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
