package sit.int221.announcement.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileResponse {

    private String fileName;
    private String fileType;
    private String fileUrl;
    private long fileSize;


    public FileResponse(String domain, String fileName,String fileType,int id,long fileSize) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileUrl = generateFileUrl(domain,id);
        this.fileSize = fileSize;
    }

    private String generateFileUrl(String domain,int folderId) {
        return domain + "/api/files/" + folderId + "/" + this.fileName;
//        return MvcUriComponentsBuilder.fromMethodName(PublicController.class,"serveFile", folderId, this.fileName).toUriString();
    }


}
