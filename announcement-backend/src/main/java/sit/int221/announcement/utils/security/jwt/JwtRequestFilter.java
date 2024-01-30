package sit.int221.announcement.utils.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
import sit.int221.announcement.exceptions.list.AuthorizedException;
import sit.int221.announcement.services.authentication.JwtUserDetailsService;
import sit.int221.announcement.enumeration.TokenType;
import sit.int221.announcement.utils.security.entra.EntraTokenUtil;
import sit.int221.announcement.utils.security.entra.EntraUser;
import sit.int221.announcement.utils.security.entrypoint.JwtAuthenticationEntryPoint;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Autowired
    private JwtUserDetailsService service;

    @Autowired
    private JwtTokenUtil util;
    @Autowired
    private EntraTokenUtil entra;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
        try {
            if (alreadyAuthenticated() && filter(request,response, chain)) return;

            String header = request.getHeader("Authorization");
            if (JwtUtil.isNotBearer(header) && filter(request, response, chain)) return;
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication auth = context.getAuthentication();
            String token = JwtUtil.getTokenFromHeader(header);
            TokenType type = TokenType.NULL;
            String username;

            try {
                if (auth == null && entra.authenticate(token)) {
                    filter(request,response,chain);
                    return;
                }
                type = util.getTokenType(token);
                username = type == TokenType.ACCESS_TOKEN ? util.getSubjectFromToken(token) : null;
            }
            catch (MalformedJwtException | SignatureException e) { throw new AuthorizedException("Token","Invalid form token"); }
            catch (IllegalArgumentException e) { throw new AuthorizedException(type.toString(),"Unable to get JWT Token"); }
            catch (ExpiredJwtException e) {
                Object expiredType = e.getClaims().get("type");
                throw new AuthorizedException(expiredType != null ? expiredType.toString() : TokenType.NULL.toString(),"Expired Token");
            }
            catch (RuntimeException e) {
                throw new AuthorizedException(TokenType.NULL.toString(), "Expired Token");
            }
            UserDetails user = service.loadUserByUsername(username);

            if (user != null && auth == null && util.validateToken(token,user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
            }

            filter(request,response,chain);
        } catch (AuthorizedException e) { entryPoint.commence(request,response,e); }

    }

    private boolean filter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        try {
            chain.doFilter(request,response);
            return true;
        } catch (ServletException | IOException e) {
            throw new AuthorizedException(e.getCause().getClass().getSimpleName(), e.getMessage());
        }

    }

    private boolean alreadyAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
}
