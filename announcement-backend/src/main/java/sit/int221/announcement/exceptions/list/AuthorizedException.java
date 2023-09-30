package sit.int221.announcement.exceptions.list;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthorizedException extends AuthenticationException {


    public AuthorizedException(String field) {
        this(field,"unauthorized");
    }
    public AuthorizedException(String field,String message) {
        super(field,new Throwable(message));
    }


}
