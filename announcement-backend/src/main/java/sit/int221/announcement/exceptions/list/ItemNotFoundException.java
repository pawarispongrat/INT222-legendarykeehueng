package sit.int221.announcement.exceptions.list;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends FieldException {

    public ItemNotFoundException(String field) {
        super(field,"does not exists");
    }
    public ItemNotFoundException(String field,String message) {
        super(field,message);
    }
}
