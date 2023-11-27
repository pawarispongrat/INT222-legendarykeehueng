package sit.int221.announcement.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sit.int221.announcement.dtos.response.announcement.AnnouncementAdminResponse;
import sit.int221.announcement.dtos.response.announcement.AnnouncementGuestResponse;
import sit.int221.announcement.dtos.request.AnnouncementRequest;
import sit.int221.announcement.dtos.PageDTO;
import sit.int221.announcement.services.AnnouncementService;
import sit.int221.announcement.enumeration.Modes;
import sit.int221.announcement.services.FileService;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService service;
    @Autowired
    private FileService fileService;

    @GetMapping("")
    public List<? extends AnnouncementGuestResponse> getAnnouncement(
            @RequestParam(defaultValue = "admin") Modes mode) {
        return service.getAnnouncement(mode);
    }
    @GetMapping("/pages")
    public PageDTO<? extends AnnouncementGuestResponse> getAnnouncementPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "admin") Modes mode,
            @RequestParam(defaultValue = "0") int category
    ){
        return service.getAnnouncementPage(page,size,mode,category);
    }
    //ADMIN & ANNOUNCER
    @PostMapping("")
    public AnnouncementAdminResponse addAnnouncement(@Valid @RequestBody AnnouncementRequest announcement) {
        return service.addAnnouncement(announcement);
    }
    @GetMapping("/{id}")
    @PreAuthorize("!isAuthenticated() || @security.authorizeAnnouncement(#id)")
    public <T extends AnnouncementGuestResponse> T getAnnouncementById( @PathVariable Integer id, @RequestParam (defaultValue = "false") boolean count) {
        return service.getAdminAnnouncementById(id,count);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@security.authorizeAnnouncement(#id)")
    public AnnouncementAdminResponse deleteAnnouncement(@PathVariable Integer id) {
        AnnouncementAdminResponse response = service.deleteAnnouncement(id);
        fileService.deleteFolder(id);
        return response;
    }
    @PutMapping("/{id}")
    @PreAuthorize("@security.authorizeAnnouncement(#id)")
    public AnnouncementAdminResponse updateAnnouncement(@PathVariable Integer id,@Valid @RequestBody AnnouncementRequest announcement) {
        return service.updateAnnouncement(id,announcement);
    }

}
