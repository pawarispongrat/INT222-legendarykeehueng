package sit.int221.announcement.utils.components;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import sit.int221.announcement.dtos.JwtUser;
import sit.int221.announcement.enumeration.Role;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserComponent {

    public String getSubject() {
        return isAuthenticated() ? getAuthentication().getName() : null;
    }
    public String getEmail() {
//        System.out.println(((JwtUser) getAuthentication().getPrincipal()));
        return isAuthenticated() && getAuthentication().getPrincipal() instanceof JwtUser jwtUser ?
                jwtUser.getEmail() :
                null;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public List<String> getAuthorities() {
        return isAuthenticated() ?  getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()) : null;
    }

    public boolean isEditor(Role... roles) {
        List<String> authorities = getAuthorities();
        if (authorities == null) return false;
        List<String> matcher = Arrays.stream(roles).map(Enum::toString).toList();
        return authorities.stream().anyMatch(matcher::contains);
    }
    public boolean isEditor() {
        return isEditor(Role.admin, Role.announcer);
    }
    public boolean isAuthenticated() {
        return getAuthentication() != null &&  getAuthentication().isAuthenticated();
    }
}
