package sit.int221.announcement.dtos.response;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Getter @Setter
public class FileResponse {

    private String fileName;
    private String fileUrl;

    public FileResponse(String fileName) {
        this.fileName = fileName;
        this.fileUrl = generateFileUrl();
    }

    private String generateFileUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/img/")
                .path(this.getFileName())
                .toUriString();
    }
}
