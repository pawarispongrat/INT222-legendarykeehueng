package sit.int221.announcement.exceptions.list.files;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import sit.int221.announcement.exceptions.list.FieldException;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFileException extends FieldException {

    public InvalidFileException(String message) {
        super("file",message);
    }
}
