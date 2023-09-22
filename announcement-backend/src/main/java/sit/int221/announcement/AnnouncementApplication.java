package sit.int221.announcement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import sit.int221.announcement.utils.security.JwtProperties;


@SpringBootApplication
@EnableConfigurationProperties({ JwtProperties.class })
public class AnnouncementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnnouncementApplication.class, args);
    }


}
