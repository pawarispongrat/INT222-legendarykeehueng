package sit.int221.announcement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import sit.int221.announcement.utils.properties.EmailProperties;
import sit.int221.announcement.utils.properties.FileProperties;
import sit.int221.announcement.utils.properties.JwtProperties;
import sit.int221.announcement.utils.security.entra.EntraProperties;


@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties({ JwtProperties.class, FileProperties.class, EmailProperties.class, EntraProperties.class})
public class AnnouncementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnnouncementApplication.class, args);
    }


}
