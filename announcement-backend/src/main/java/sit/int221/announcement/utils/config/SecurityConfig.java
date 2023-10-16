package sit.int221.announcement.utils.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import sit.int221.announcement.utils.enums.Role;
import sit.int221.announcement.utils.security.Argon;
import sit.int221.announcement.utils.security.entrypoint.JwtAccessDeniedEntryPoint;
import sit.int221.announcement.utils.security.entrypoint.JwtAuthenticationEntryPoint;
import sit.int221.announcement.utils.security.JwtRequestFilter;
import sit.int221.announcement.services.JwtUserDetailsService;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private final AuthenticationConfiguration auth;
    @Autowired
    private JwtRequestFilter filter;
    @Autowired
    private JwtUserDetailsService service;
    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Autowired
    private JwtAccessDeniedEntryPoint accessDenied;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return auth.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/token").permitAll()
                        .requestMatchers(GET,"/api/announcements/**").permitAll()
                        .requestMatchers(matchers("/api/announcements/**",POST,PUT,DELETE)).hasAnyAuthority(Role.admin.toString(),Role.announcer.toString())
                        .requestMatchers(DELETE,"/api/users/**").hasAuthority(Role.admin.toString())
                        .requestMatchers("/api/users").hasAuthority(Role.admin.toString())
                        .anyRequest().authenticated())
                        .authenticationProvider(authenticationProvider())
                        .exceptionHandling().authenticationEntryPoint(entryPoint).accessDeniedHandler(accessDenied).and()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    private RequestMatcher[] matchers(String path,HttpMethod... methods) {
        return Arrays.stream(methods).map(method -> new AntPathRequestMatcher(method.name(), path)).toArray(RequestMatcher[]::new);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon().getEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(service);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}