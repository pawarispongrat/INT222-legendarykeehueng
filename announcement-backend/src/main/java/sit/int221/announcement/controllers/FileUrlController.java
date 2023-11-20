package sit.int221.announcement.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.int221.announcement.services.AnnouncementService;
import sit.int221.announcement.services.FileService;

import java.io.IOException;

@RestController
@RequestMapping("/img/")
public class FileUrlController {

    @Autowired
    private FileService service;
    @Autowired
    private AnnouncementService announcement;

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

}
