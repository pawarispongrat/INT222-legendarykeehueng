package sit.int221.announcement.exceptions.list;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UserException extends AuthenticationException {


    public UserException(String field) {
        this(field,"does not exists");
    }
    public UserException(String field,String message) {
        super(field,new Throwable(message));
    }

}
