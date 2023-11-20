package sit.int221.announcement.services;

import jakarta.servlet.http.HttpServletRequest;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    public FileResponse store(MultipartFile file, Integer folderId) throws IOException {
        String originalName = file.getOriginalFilename();
        if (originalName == null) throw new InvalidFileException("Original file name cannot be null");
        String fileName = StringUtils.cleanPath(originalName);

        if (fileName.contains("..")) throw new InvalidFileException("File name invalid " + fileName);
        Path target = getTargetPath(fileName,folderId);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return new FileResponse(fileName,folderId);

    }

    public void delete(String fileName,Integer folderId) throws IOException {
        Path target = getTargetPath(fileName,folderId);
        boolean exists = Files.deleteIfExists(target);
        if (!exists) throw new FileNotFoundException("File not exists!");
    }

    public Resource loadFileAsResource(String fileName,Integer folderId) throws IOException {
        Path target = getTargetPath(fileName,folderId);
        Resource resource = new UrlResource(target.toUri());
        if (resource.exists()) return resource;
        else throw new FileNotFoundException("File not found.");
    }

    public long getFileCount(Integer folderId) {
        try {
            Path location = getUploadPath(folderId);
            try (Stream<Path> paths = Files.list(location)) {
                return paths.filter(path -> path.toFile().isFile()).count();
            }
        } catch (IOException e) { return 0; }
    }

    private Path getTargetPath(String filename, Integer folderId) throws IOException {
        Path location = getUploadPath(folderId);
        if (Files.notExists(location)) Files.createDirectories(location);
        return location.resolve(filename).normalize();
    }

    private Path getUploadPath(Integer folderId) {
        return Paths.get(properties.getUploadDir() + "/" + folderId).toAbsolutePath().normalize();
    }
}
