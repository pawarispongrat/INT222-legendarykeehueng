package sit.int221.announcement.utils.security;

import java.io.IOException;
import java.io.OutputStream;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sit.int221.announcement.exceptions.ErrorResponse;
import sit.int221.announcement.exceptions.list.AuthorizedException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        int status = HttpServletResponse.SC_UNAUTHORIZED;
        ErrorResponse error = new ErrorResponse(status,e.getClass().getSimpleName(), request.getRequestURI());
        if (e instanceof AuthorizedException) error.addValidationError(e.getMessage(),e.getCause().getMessage());
        sendResponse(status,response,error);
    }

    private void sendResponse(int status, HttpServletResponse response,ErrorResponse error) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status);
        response.getWriter().write(mapper.writeValueAsString(error));
    }
}
