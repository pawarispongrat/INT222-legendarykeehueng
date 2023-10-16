package sit.int221.announcement.utils.security.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import sit.int221.announcement.exceptions.ErrorResponse;
import sit.int221.announcement.exceptions.list.AuthorizedException;

import java.io.IOException;

@Component
public class JwtAccessDeniedEntryPoint implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        int status = HttpServletResponse.SC_FORBIDDEN;
        ErrorResponse error = new ErrorResponse(status,"SC_FORBIDDEN", request.getRequestURI());
        error.sendResponse(response);
    }
}
