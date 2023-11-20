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

    public FileResponse(String fileName,int id) {
        this.fileName = fileName;
        this.fileUrl = generateFileUrl(id);
    }

    private String generateFileUrl(int id) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/img/" + id + "/")
                .path(this.getFileName())
                .toUriString();
    }
}
