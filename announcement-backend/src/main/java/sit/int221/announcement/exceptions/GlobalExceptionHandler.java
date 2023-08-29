package sit.int221.announcement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import sit.int221.announcement.exceptions.list.ItemNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,WebRequest request) {
        ErrorResponse response = new ErrorResponse(BAD_REQUEST.value(),e.getClass().getSimpleName(),getUri(request));
        e.getBindingResult().getFieldErrors().forEach((field) -> {
            response.addValidationError(field.getField(),field.getDefaultMessage());
        });
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public  ResponseEntity<ErrorResponse> handleNotFound(ItemNotFoundException e , WebRequest request){
        ErrorResponse response = new ErrorResponse(NOT_FOUND.value(),e.getField(),getUri(request)) ;
        response.addValidationError(e.getField(),e.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(response) ;
    }

    private String getUri(WebRequest request) {
        return request.getDescription(false).substring(4);
    }
}
