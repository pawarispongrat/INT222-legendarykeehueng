package sit.int221.announcement.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sit.int221.announcement.exceptions.ErrorResponse;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage {

    private List<ResponseDetail> detail;
    private List<ExistsDetail> exists;

    private String message;

    public ResponseMessage(String message) {
        this.message = message;
        this.detail = null;
        this.exists = null;
    }

    public void addDetail(String field,String message) {
        if (this.detail == null) this.detail = new ArrayList<>();
        this.detail.add(new ResponseDetail(field, message));
    }

    public void addExist(String field,boolean isExist) {
        if (this.exists == null) this.exists = new ArrayList<>();
        this.exists.add(new ExistsDetail(field, isExist));
    }

    @Getter @Setter
    @AllArgsConstructor
    static class ResponseDetail {
        private String field;
        private String message;
    }

    @Getter @Setter
    @AllArgsConstructor
    static class ExistsDetail {
        private String id;
        private boolean exist;
    }
}
