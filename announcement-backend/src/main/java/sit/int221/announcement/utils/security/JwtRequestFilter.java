package sit.int221.announcement.utils.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
import sit.int221.announcement.exceptions.list.AuthorizedException;
import sit.int221.announcement.exceptions.list.ItemNotFoundException;
import sit.int221.announcement.utils.enums.Token;
import sit.int221.announcement.utils.security.JwtUserDetailsService;
import sit.int221.announcement.utils.security.JwtTokenUtil;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService service;

    @Autowired
    private JwtTokenUtil util;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
            String header = request.getHeader("Authorization");
            if (!JwtUtil.isBearer(header)) { chain.doFilter(request,response); return; }

            String token = JwtUtil.getTokenFromHeader(header);
            Token type = util.getTokenType(token);
            String username = null;

            try { username = type == Token.ACCESS_TOKEN ? util.getUsernameFromToken(token) : null; }
//            catch (IllegalArgumentException e) { throw new AuthorizedException(type.toString(), "Unable to get JWT Token"); }
//            catch (ExpiredJwtException e) { throw new AuthorizedException(type.toString(), "Expired"); }
//            catch (Exception e) { throw new AuthorizedException("Exception"); }
            catch (IllegalArgumentException e) { logger.warn("Unable to get JWT Token"); }
            catch (ExpiredJwtException e) { logger.warn("Expired"); }
            catch (Exception e) { logger.warn("Exception"); }

            SecurityContext context = SecurityContextHolder.getContext();
            Authentication auth = context.getAuthentication();
            UserDetails user = service.loadUserByUsername(username);
            if (user != null && auth == null && util.validateToken(token,user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
            }

            chain.doFilter(request, response);
    }
}
