package sit.int221.announcement.utils.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import sit.int221.announcement.utils.enums.TokenType;

import java.util.Map;

@ConfigurationProperties(prefix = "file")
@Getter @Setter
public class FileProperties {

    private String uploadDir;
}
