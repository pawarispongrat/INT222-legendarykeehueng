package sit.int221.announcement.utils.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import sit.int221.announcement.utils.security.Argon;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration auth;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return auth.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.OPTIONS).permitAll() // allow CORS option calls for Swagger UI
                        .requestMatchers("/api/token","/api/users").permitAll()
                        .anyRequest().authenticated())
                .httpBasic();
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon().getEncoder();
    }
}