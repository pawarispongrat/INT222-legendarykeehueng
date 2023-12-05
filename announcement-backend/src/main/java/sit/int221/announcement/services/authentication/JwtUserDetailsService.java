package sit.int221.announcement.services.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    private Collection<GrantedAuthority> getAuthority(sit.int221.announcement.models.User user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        return authorities;
    }
}
