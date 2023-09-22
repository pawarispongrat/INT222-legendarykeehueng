package sit.int221.announcement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService service;
    @Override
    public UserDetails loadUserByUsername(String username) {
        sit.int221.announcement.models.User user = service.getUserByUsername(username);
        return new User(user.getUsername(),user.getEncodedPassword(),new ArrayList<>());
    }
}
