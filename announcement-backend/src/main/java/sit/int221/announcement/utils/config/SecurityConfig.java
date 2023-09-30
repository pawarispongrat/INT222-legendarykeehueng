package sit.int221.announcement.utils.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sit.int221.announcement.utils.security.Argon;
import sit.int221.announcement.utils.security.JwtAuthenticationEntryPoint;
import sit.int221.announcement.utils.security.JwtRequestFilter;
import sit.int221.announcement.utils.security.JwtUserDetailsService;


import static org.springframework.http.HttpMethod.OPTIONS;
@Configuration
@EnableWebSecurity
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

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return auth.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests((requests) -> requests
                        .requestMatchers(OPTIONS).permitAll() // allow CORS option calls for Swagger UI
                        .requestMatchers("/api/token").permitAll()
                        .anyRequest().authenticated())
                        .authenticationProvider(authenticationProvider())
                        .exceptionHandling().authenticationEntryPoint(entryPoint).and()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                        .httpBasic();
        return http.build();
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