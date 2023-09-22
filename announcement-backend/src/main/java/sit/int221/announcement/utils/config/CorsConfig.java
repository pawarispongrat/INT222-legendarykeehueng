package sit.int221.announcement.utils.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowedOrigins("http://localhost:5173",
                                "https://intproj22.sit.kmutt.ac.th/",
                                "http://intproj22.sit.kmutt.ac.th/",
                                "http://25.38.200.142:5173",
                                "http://25.37.174.170:5173");
            }
        };
    }
}
