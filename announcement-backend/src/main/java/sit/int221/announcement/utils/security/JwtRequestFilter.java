package sit.int221.announcement.utils.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
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
            if (header == null || !header.startsWith("Bearer ")) { chain.doFilter(request,response); return; }
            else logger.warn("JWT Token does not begin with Bearer String");
            String token = header.substring(7).trim();
            String username = null;

            try {  username = util.getUsernameFromToken(token); }
            catch (IllegalArgumentException e) { System.out.println("Unable to get JWT Token"); }
            catch (ExpiredJwtException e) { System.out.println("JWT Token has expired"); }
            SecurityContext context = SecurityContextHolder.getContext();
            System.out.println(username);
            if (username == null || context.getAuthentication() != null) { chain.doFilter(request, response); return; }
            UserDetails user = service.loadUserByUsername(username);
            if (!util.validateToken(token,user)) { chain.doFilter(request, response); return; }

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            context.setAuthentication(auth);

            chain.doFilter(request, response);
    }
}
