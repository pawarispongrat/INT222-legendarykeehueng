package sit.int221.announcement.dtos.response;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sit.int221.announcement.controllers.PublicController;

@Getter @Setter
public class FileResponse {

    private String fileName;
    private String fileType;
    private String fileUrl;
    private long fileSize;


    public FileResponse(String fileName,String fileType,int id,long fileSize) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileUrl = generateFileUrl(id);
        this.fileSize = fileSize;
    }

    private String generateFileUrl(int folderId) {
        return MvcUriComponentsBuilder.fromMethodName(PublicController.class,"serveFile", folderId, this.fileName).toUriString();
    }


}
