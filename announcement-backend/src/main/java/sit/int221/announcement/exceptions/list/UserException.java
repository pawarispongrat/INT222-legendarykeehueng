package sit.int221.announcement.exceptions.list;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UserException extends FieldException {


    public UserException(String field) {
        super(field,"unauthentication");
    }
    public UserException(String field,String message) {
        super(field,message);
    }

}
