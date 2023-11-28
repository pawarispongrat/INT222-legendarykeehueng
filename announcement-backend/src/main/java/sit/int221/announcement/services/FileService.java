package sit.int221.announcement.services;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sit.int221.announcement.dtos.response.FileResponse;
import sit.int221.announcement.exceptions.list.files.InvalidFileException;
import sit.int221.announcement.exceptions.list.files.FileNotFoundException;
import sit.int221.announcement.utils.properties.FileProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileService {

    @Autowired
    private FileProperties properties;

    public String getFileMime(HttpServletRequest request,Resource resource) {
        try {
            String path = resource.getFile().getAbsolutePath();
            return request.getServletContext().getMimeType(path);
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
            return null;
        }
    }
    public String getFileMime(String fileName, Integer folderId) {
        return getFileMime( getUploadFile(fileName,folderId) );
    }

    public String getFileMime(Path path) {
        try {
            return Files.probeContentType(path);
        } catch (IOException e) {
            return "application/octet-stream";
        }
    }

    public List<FileResponse> getFilesByAnnouncementId(Integer folderId) {
        Path folderPath = getUploadPath(folderId);
        try (Stream<Path> path = Files.walk(folderPath).filter((filePath) ->
                !filePath.toFile().getName().startsWith(".")
                && Files.isRegularFile(filePath))) {
            return path.map((filePath) -> {
                File file = filePath.toFile();
                String name = file.getName();
                Path upload = getUploadFile(name,folderId);
                return new FileResponse(name, getFileMime(upload), folderId, file.length());
            }).collect(Collectors.toList());
        } catch (IOException ignored) { return new ArrayList<>(); }
    }

    public FileResponse store(MultipartFile file, Integer folderId) throws IOException {
        String originalName = file.getOriginalFilename();
        if (originalName == null) throw new InvalidFileException("Original file name cannot be null");
        String fileName = StringUtils.cleanPath(originalName);

        if (fileName.contains("..")) throw new InvalidFileException("File name invalid " + fileName);
        Path target = getTargetPath(fileName,folderId);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return new FileResponse(fileName,file.getContentType(),folderId, file.getSize());

    }


    public void delete(String fileName,Integer folderId) throws IOException {
        Path target = getTargetPath(fileName,folderId);
        boolean exists = Files.deleteIfExists(target);
        if (!exists) throw new FileNotFoundException("File not exists!");
    }
    public boolean deleteFile(String fileName,Integer folderId) {
        try {
            Path target = getTargetPath(fileName,folderId);
            return Files.deleteIfExists(target);
        } catch (IOException e) {
            return false;
        }
    }
    public void deleteFolder(Integer folderId) {
        Path target = getUploadPath(folderId);
        try { Files.deleteIfExists(target); }
        catch (IOException ignored) {}
    }
    public void deleteFilesInFolder(Integer folderId) {
        try {
            Path target = getUploadPath(folderId);
            FileUtils.cleanDirectory(target.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Resource loadFileAsResource(String fileName,Integer folderId) throws IOException {
        if (fileName.startsWith(".")) throw new java.io.FileNotFoundException("File not found.");
        Path target = getTargetPath(fileName,folderId);
        Resource resource = new UrlResource(target.toUri());
        if (resource.exists()) return resource;
        else throw new FileNotFoundException("File not found.");
    }

    public long getFileCount(Integer folderId) {
        try {
            Path location = getUploadPath(folderId);
            try (Stream<Path> paths = Files.list(location)) {
                return paths.filter(this::isFile).count();
            }
        } catch (IOException e) { return 0; }
    }

    private Path getTargetPath(String filename, Integer folderId) throws IOException {
        Path location = getUploadPath(folderId);
        if (Files.notExists(location)) Files.createDirectories(location);
        return location.resolve(filename).normalize();
    }

    private boolean isFile(Path filePath) {
        return !filePath.toFile().getName().startsWith(".") && Files.isRegularFile(filePath);
    }
    private Path getUploadFile(String filename, Integer folderId) {
        return getUploadPath(folderId).resolve(filename).normalize();
    }

    private Path getUploadPath(Integer folderId) {
        return Paths.get(properties.getUploadDir() + "/" + folderId).toAbsolutePath().normalize();
    }
}
