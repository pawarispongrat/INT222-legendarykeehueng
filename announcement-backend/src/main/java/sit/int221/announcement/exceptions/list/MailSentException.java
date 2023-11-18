package sit.int221.announcement.exceptions.list;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class MailSentException extends FieldException {

    public MailSentException() {
        super("email","does not sent");
    }
}
