package sit.int221.announcement.utils.security;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
import sit.int221.announcement.exceptions.list.AuthorizedException;
import sit.int221.announcement.utils.enums.Token;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Autowired
    private JwtUserDetailsService service;

    @Autowired
    private JwtTokenUtil util;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String header = request.getHeader("Authorization");
            if (JwtUtil.isNotBearer(header)) { chain.doFilter(request,response); return; }
            String token = JwtUtil.getTokenFromHeader(header);
            Token type = Token.NULL;
            String username;
            try {
                type = util.getTokenType(token);
                username = type == Token.ACCESS_TOKEN ? util.getUsernameFromToken(token) : null;
            }
            catch (MalformedJwtException | SignatureException e) { throw new AuthorizedException("Token","Invalid form token"); }
            catch (IllegalArgumentException e) { throw new AuthorizedException(type.toString(),"Unable to get JWT Token"); }
            catch (ExpiredJwtException e) { throw new AuthorizedException(e.getClaims().get("type").toString(),"Expired Token"); }

            SecurityContext context = SecurityContextHolder.getContext();
            Authentication auth = context.getAuthentication();
            UserDetails user = service.loadUserByUsername(username);
            if (user != null && auth == null && util.validateToken(token,user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
            }

            chain.doFilter(request, response);
        } catch (AuthorizedException e) { entryPoint.commence(request,response,e); }

    }
}
