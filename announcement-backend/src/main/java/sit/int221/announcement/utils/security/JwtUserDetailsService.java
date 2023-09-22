package sit.int221.announcement.utils.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import sit.int221.announcement.exceptions.list.ItemNotFoundException;
import sit.int221.announcement.repositories.UserRepository;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        sit.int221.announcement.models.User user = repository.findByUsername(username).orElseThrow(() -> new ItemNotFoundException("username"));
        return new User(user.getUsername(),user.getPassword(),new ArrayList<>());
    }
}
