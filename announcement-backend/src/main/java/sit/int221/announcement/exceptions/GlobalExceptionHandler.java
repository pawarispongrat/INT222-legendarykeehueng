package sit.int221.announcement.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import sit.int221.announcement.exceptions.list.ItemNotFoundException;
import sit.int221.announcement.exceptions.list.AuthorizedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    private final HttpStatus UNAUTHORIZED = HttpStatus.UNAUTHORIZED;
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,WebRequest request) {
        ErrorResponse response = new ErrorResponse(BAD_REQUEST.value(),getSimpleName(e),getUri(request));
        e.getBindingResult().getFieldErrors().forEach((field) -> {
            response.addValidationError(field.getField(),field.getDefaultMessage());
        });
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleNotFound(ConstraintViolationException e , WebRequest request){
        ErrorResponse response = new ErrorResponse(BAD_REQUEST.value(),getSimpleName(e),getUri(request)) ;
        System.out.println(e.getConstraintName());
        return ResponseEntity.status(BAD_REQUEST).body(response) ;
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public  ResponseEntity<ErrorResponse> handleNotFound(ItemNotFoundException e , WebRequest request){
        ErrorResponse response = new ErrorResponse(NOT_FOUND.value(),getSimpleName(e),getUri(request)) ;
        response.addValidationError(e.getField(),e.getCause().getMessage());
        return ResponseEntity.status(NOT_FOUND).body(response) ;
    }

    @ExceptionHandler(AuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public  ResponseEntity<ErrorResponse> handleUnauthorized(AuthorizedException e , WebRequest request){
        ErrorResponse response = new ErrorResponse(UNAUTHORIZED.value(),getSimpleName(e),getUri(request)) ;
        response.addValidationError(e.getField(),e.getCause().getMessage());
        return ResponseEntity.status(UNAUTHORIZED).body(response) ;
    }

    private String getSimpleName(Object object) { return object.getClass().getSimpleName(); }
    private String getUri(WebRequest request) {
        return request.getDescription(false).substring(4);
    }
}