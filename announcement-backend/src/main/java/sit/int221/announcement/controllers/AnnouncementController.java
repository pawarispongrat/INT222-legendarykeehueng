package sit.int221.announcement.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sit.int221.announcement.dtos.response.announcement.AnnouncementAdminResponse;
import sit.int221.announcement.dtos.response.announcement.AnnouncementGuestResponse;
import sit.int221.announcement.dtos.request.AnnouncementRequest;
import sit.int221.announcement.dtos.PageDTO;
import sit.int221.announcement.models.Announcement;
import sit.int221.announcement.services.AnnouncementService;
import sit.int221.announcement.utils.enums.Modes;
import sit.int221.announcement.utils.enums.Role;
import sit.int221.announcement.utils.security.JwtTokenUtil;
import sit.int221.announcement.utils.security.JwtUtil;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService service;
    @Autowired
    private JwtTokenUtil jwt;



    @GetMapping("")
    public List<? extends AnnouncementGuestResponse> getAnnouncement(
            @RequestHeader(value = "Authorization", required = false) String header,
            @RequestParam(defaultValue = "admin") Modes mode) {
        String token = JwtUtil.getTokenFromHeader(header);
        String username = token != null ? jwt.getUsernameFromToken(token) : null;
        List<String> authorities = token != null ? jwt.getAuthoritiesFromToken(token) : null;
        return service.getAnnouncement(mode,username,authorities);
    }
    @GetMapping("/pages")
    public PageDTO<AnnouncementGuestResponse> getAnnouncementPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "admin") Modes mode,
            @RequestParam(defaultValue = "0") int category
    ){
        return service.getAnnouncementPage(page,size,mode,category);
    }
    //ADMIN & ANNOUNCER
    @PostMapping("")
    public AnnouncementAdminResponse addAnnouncement(@RequestHeader(value = "Authorization") String header, @Valid @RequestBody AnnouncementRequest announcement) {
        String token = JwtUtil.getTokenFromHeader(header);
        if (token == null) return null;
        String username = jwt.getUsernameFromToken(token);
        return service.addAnnouncement(announcement,username);
    }
    @GetMapping("/{id}")
    @PreAuthorize("!isAuthenticated() || @security.authorizeAnnouncement(#id)")
    public <T extends AnnouncementGuestResponse> T getAnnouncementById( @RequestHeader(value = "Authorization", required = false) String header,
                                                          @PathVariable Integer id,
                                                          @RequestParam (defaultValue = "false") boolean count) {
        String token = JwtUtil.getTokenFromHeader(header);
        List<String> authorities = token != null ? jwt.getAuthoritiesFromToken(token) : null;
        return service.getAdminAnnouncementById(id,count,authorities);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@security.authorizeAnnouncement(#id)")
    public AnnouncementAdminResponse deleteAnnouncement(@PathVariable Integer id){
        return service.deleteAnnouncement(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("@security.authorizeAnnouncement(#id)")
    public AnnouncementAdminResponse updateAnnouncement(@PathVariable Integer id,@Valid @RequestBody AnnouncementRequest announcement) {
        return service.updateAnnouncement(id,announcement);
    }

}
