package sit.int221.announcement.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sit.int221.announcement.dtos.response.FileResponse;
import sit.int221.announcement.enumeration.Role;
import sit.int221.announcement.exceptions.list.files.InvalidFileException;
import sit.int221.announcement.services.AnnouncementService;
import sit.int221.announcement.services.FileService;
import sit.int221.announcement.utils.ResponseMessage;
import sit.int221.announcement.utils.components.UserComponent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService service;
    @Autowired
    private AnnouncementService announcement;
    @Autowired
    private UserComponent authenticate;

    @GetMapping("/{announcementId}")
    @PreAuthorize("!isAuthenticated() || @security.authorizeAnnouncement(#announcementId)")
    public List<FileResponse> getFilesFromAnnouncementId(@PathVariable Integer announcementId) {
        if (authenticate.isEditor()) announcement.hasAnnouncement(announcementId);
        else announcement.isDisplay(announcementId);

        return service.getFilesByAnnouncementId(announcementId) ;
    }

    @PostMapping("/{announcementId}")
    @PreAuthorize("@security.authorizeAnnouncement(#announcementId)")
    public List<FileResponse> upload(
            @PathVariable Integer announcementId,
            @RequestParam("files") MultipartFile[] files
    ) throws IOException {
        announcement.hasAnnouncement(announcementId);

        int MAX_FILE = 5;
        long fileCount = files.length + service.getFileCount(announcementId);
        if (fileCount > MAX_FILE) throw new InvalidFileException(String.format("Exceeding file count: %s", MAX_FILE));

        List<FileResponse> responses = new ArrayList<>();
        for (MultipartFile file : files) responses.add(service.store(file,announcementId));

        return responses;
    }

    @PutMapping("/{announcementId}")
    @PreAuthorize("@security.authorizeAnnouncement(#announcementId)")
    public List<FileResponse> update(
            @PathVariable Integer announcementId,
            @RequestParam("files") MultipartFile[] files
    ) throws IOException {
        service.deleteFilesInFolder(announcementId);

        return upload(announcementId,files);
    }

    @DeleteMapping("/{announcementId}/{filename:.+}")
    @PreAuthorize("@security.authorizeAnnouncement(#announcementId)")
    public ResponseMessage delete(
            @PathVariable Integer announcementId,
            @PathVariable String filename) throws IOException {
        announcement.hasAnnouncement(announcementId);

        service.delete(filename,announcementId);

        return new ResponseMessage("Delete the file successfully: " + filename);
    }
}
