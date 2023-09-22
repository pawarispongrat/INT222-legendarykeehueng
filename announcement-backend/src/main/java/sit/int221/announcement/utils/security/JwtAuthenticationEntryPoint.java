package sit.int221.announcement.utils.security;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import sit.int221.announcement.exceptions.ErrorResponse;
import sit.int221.announcement.utils.WebUtils;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        int status = HttpServletResponse.SC_UNAUTHORIZED;
        ErrorResponse error = new ErrorResponse(status,"UNAUTHORIZED", request.getRequestURI());
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setStatus(status);
        OutputStream out = response.getOutputStream();
        mapper.writeValue(out,error);
        out.flush();
    }
}
