package sit.int221.announcement.services.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import sit.int221.announcement.dtos.JwtUser;
import sit.int221.announcement.exceptions.list.ItemNotFoundException;
import sit.int221.announcement.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        if (username == null) return null;
        sit.int221.announcement.models.User user = repository.findByUsername(username).orElse(null);
        if (user == null) return null;
        return new JwtUser(user.getUsername(),user.getEmail(), user.getName(),user.getPassword(),getAuthority(user));
    }

    public JwtUser loadUserByEmail(String email) {
        if (email == null) return null;
        sit.int221.announcement.models.User user = repository.findByEmail(email).orElse(null);
        if (user == null) return null;
        return new JwtUser(user.getUsername(),user.getEmail(), user.getName(),user.getPassword(),getAuthority(user));
    }

    public void authenticate(JwtUser user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        if (request != null) authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private Collection<GrantedAuthority> getAuthority(sit.int221.announcement.models.User user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        return authorities;
    }
}
