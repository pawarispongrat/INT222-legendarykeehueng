package sit.int221.announcement.utils.security;

import java.io.IOException;
import java.io.OutputStream;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sit.int221.announcement.exceptions.ErrorResponse;
import sit.int221.announcement.exceptions.list.AuthorizedException;

@Component
@ControllerAdvice
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        int status = HttpServletResponse.SC_UNAUTHORIZED;
        ErrorResponse error = new ErrorResponse(status,"UNAUTHORIZED", request.getRequestURI());
        sendResponse(status,response,error);
    }

    @ExceptionHandler(value = { AuthorizedException.class })
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthorizedException e) throws IOException {
        int status = HttpServletResponse.SC_UNAUTHORIZED;
        ErrorResponse error = new ErrorResponse(status,"UNAUTHORIZED", request.getRequestURI());
        error.addValidationError(e.getField(),e.getMessage());
        sendResponse(status,response,error);
    }

    private void sendResponse(int status, HttpServletResponse response,ErrorResponse error) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setStatus(status);
        OutputStream out = response.getOutputStream();
        mapper.writeValue(out,error);
        out.flush();
    }
}
