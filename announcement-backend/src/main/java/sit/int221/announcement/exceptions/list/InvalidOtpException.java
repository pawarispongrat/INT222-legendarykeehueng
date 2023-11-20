package sit.int221.announcement.exceptions.list;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidOtpException extends FieldException {

    public InvalidOtpException() {
        super("otp","Otp is invalid");
    }
}
