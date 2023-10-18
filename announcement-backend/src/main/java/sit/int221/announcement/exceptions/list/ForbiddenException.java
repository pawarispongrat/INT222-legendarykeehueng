package sit.int221.announcement.exceptions.list;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends AuthenticationException {


    public ForbiddenException(String field) {
        this(field,"Forbidden exception");
    }
    public ForbiddenException(String field, String message) {
        super(field,new Throwable(message));
    }

}
