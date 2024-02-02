package sit.int221.announcement.utils.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import sit.int221.announcement.dtos.JwtUser;
import sit.int221.announcement.models.Announcement;
import sit.int221.announcement.models.User;
import sit.int221.announcement.services.AnnouncementService;
import sit.int221.announcement.services.UserService;
import sit.int221.announcement.enumeration.Role;

import java.util.Collection;
import java.util.List;

@Component(value = "security")
public class SecurityComponent {

    @Autowired
    private AnnouncementService announcementService;

    public boolean authorizeAnnouncement(Integer announcementId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.contains(new SimpleGrantedAuthority(Role.admin.toString()))) return true;
        JwtUser details = (JwtUser) authentication.getPrincipal();
        List<Integer> announcements = announcementService.getAnnouncementsByEmail(details.getEmail()).stream().map(Announcement::getId).toList();
        return authorities.contains(new SimpleGrantedAuthority(Role.announcer.toString())) && announcements.contains(announcementId);
    }
}
