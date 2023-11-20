package sit.int221.announcement.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import sit.int221.announcement.exceptions.list.*;
import sit.int221.announcement.exceptions.list.files.InvalidFileException;
import sit.int221.announcement.exceptions.list.files.FileNotFoundException;
import sit.int221.announcement.utils.Utils;

import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,WebRequest request) {
        ErrorResponse response = new ErrorResponse(BAD_REQUEST.value(),getSimpleName(e), Utils.getUri(request));
        e.getBindingResult().getFieldErrors().forEach((field) -> {
            response.addValidationError(field.getField(),field.getDefaultMessage());
        });
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException e,WebRequest request) {
        ErrorResponse response = new ErrorResponse(FORBIDDEN.value(),getSimpleName(e), Utils.getUri(request)) ;
        response.addValidationError(e.getMessage(),e.getCause().getMessage());
        return ResponseEntity.status(FORBIDDEN).body(response) ;
    }

    @ExceptionHandler({ ItemNotFoundException.class , FileNotFoundException.class } )
    @ResponseStatus(NOT_FOUND)
    public  ResponseEntity<ErrorResponse> handleNotFound(FieldException e , WebRequest request){
        ErrorResponse response = new ErrorResponse(NOT_FOUND.value(),getSimpleName(e), Utils.getUri(request)) ;
        response.addValidationError(e.getField(),e.getCause().getMessage());
        return ResponseEntity.status(NOT_FOUND).body(response) ;
    }

    @ExceptionHandler({AuthorizedException.class, UserException.class})
    @ResponseStatus(UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleUnauthorized(AuthenticationException e , WebRequest request){
        ErrorResponse response = new ErrorResponse(UNAUTHORIZED.value(),getSimpleName(e), Utils.getUri(request)) ;
        response.addValidationError(e.getMessage(),e.getCause().getMessage());
        return ResponseEntity.status(UNAUTHORIZED).body(response) ;
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleIOException(IOException e, WebRequest request) {
        ErrorResponse response = new ErrorResponse(BAD_REQUEST.value(), getSimpleName(e), Utils.getUri(request));
        response.addValidationError("operationError", "See more in console for extend error.");
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @ExceptionHandler({ InvalidFileException.class, InvalidOtpException.class })
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleFileInvalid(FieldException e, WebRequest request) {
        ErrorResponse response = new ErrorResponse(BAD_REQUEST.value(), getSimpleName(e), Utils.getUri(request));
        response.addValidationError(e.getField(), e.getCause().getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MailSentException.class)
    @ResponseStatus(BAD_GATEWAY)
    public ResponseEntity<ErrorResponse> handleEmailSent(MailSentException e, WebRequest request) {
        ErrorResponse response = new ErrorResponse(BAD_GATEWAY.value(), getSimpleName(e), Utils.getUri(request));
        response.addValidationError(e.getField(), e.getCause().getMessage());
        return ResponseEntity.status(BAD_GATEWAY).body(response);
    }

    private String getSimpleName(Object object) { return object.getClass().getSimpleName(); }
}