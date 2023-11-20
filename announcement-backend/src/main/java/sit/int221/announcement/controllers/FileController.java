package sit.int221.announcement.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sit.int221.announcement.dtos.response.FileResponse;
import sit.int221.announcement.exceptions.list.files.InvalidFileException;
import sit.int221.announcement.services.AnnouncementService;
import sit.int221.announcement.services.FileService;
import sit.int221.announcement.utils.ResponseMessage;

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
    private final int FILE_LIMIT = 5;

    @PostMapping("/{announcementId}")
    public List<FileResponse> upload(
            @PathVariable Integer announcementId,
            @RequestParam("files") MultipartFile[] files) throws IOException {
        announcement.hasAnnouncement(announcementId);
        long fileCount = files.length + service.getFileCount(announcementId);
        if (fileCount > FILE_LIMIT) throw new InvalidFileException("File limit exceeded. [Max: 5]");
        List<FileResponse> responses = new ArrayList<>();
        for (MultipartFile file : files)
            responses.add(service.store(file,announcementId));

        return responses ;
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
