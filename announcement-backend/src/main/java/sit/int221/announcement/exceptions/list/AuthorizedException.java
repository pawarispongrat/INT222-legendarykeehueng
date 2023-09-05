package sit.int221.announcement.exceptions.list;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthorizedException extends RuntimeException {

    private final String field;

    public AuthorizedException(String field) {
        super(field,new Throwable("unauthorized"));
        this.field = field;
    }

    public AuthorizedException(String field, String message) {
        super(field,new Throwable(message));
        this.field = field;
    }

}
