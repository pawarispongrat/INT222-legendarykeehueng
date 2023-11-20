package sit.int221.announcement.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sit.int221.announcement.exceptions.list.files.InvalidFileException;
import sit.int221.announcement.services.AnnouncementService;
import sit.int221.announcement.services.FileService;
import sit.int221.announcement.utils.ResponseMessage;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService service;
    @Autowired
    private AnnouncementService announcement;
    private final int FILE_LIMIT = 5;

    @GetMapping("/{announcementId}/{filename:.+}")
    public ResponseEntity<Resource> serveFile(
            @PathVariable Integer announcementId,
            @PathVariable String filename,
            HttpServletRequest request) throws IOException {
        announcement.hasAnnouncement(announcementId);

        Resource resource = service.loadFileAsResource(filename, announcementId);
        String mime = service.getFileMime(request,resource);
        if (mime == null) mime = "application/octet-stream";
        //inline = view in browser, attachment = download
        String headers = String.format("inline; filename %s",filename);

        return  ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, headers)
                .contentType(MediaType.parseMediaType(mime))
                .body(resource);
    }

    @PostMapping("/{announcementId}")
    public ResponseMessage upload(
            @PathVariable Integer announcementId,
            @RequestParam("files") MultipartFile[] files) throws IOException {
        announcement.hasAnnouncement(announcementId);
        long fileCount = files.length + service.getFileCount(announcementId);
        if (fileCount > FILE_LIMIT) throw new InvalidFileException("File limit exceeded. [Max: 5]");
        for (MultipartFile file : files) service.store(file,announcementId);

        return new ResponseMessage("Upload the file successfully: " + files.length + " file");
    }

    @DeleteMapping("/{announcementId}/{filename:.+}")
    public ResponseMessage delete(
            @PathVariable Integer announcementId,
            @PathVariable String filename) throws IOException {
        announcement.hasAnnouncement(announcementId);

        service.delete(filename,announcementId);

        return new ResponseMessage("Delete the file successfully: " + filename);
    }
}
