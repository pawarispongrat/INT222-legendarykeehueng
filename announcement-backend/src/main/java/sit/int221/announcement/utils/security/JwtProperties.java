package sit.int221.announcement.utils.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import sit.int221.announcement.utils.enums.TokenType;

import java.util.Map;

@ConfigurationProperties(prefix = "jwt")
@Getter @Setter
public class JwtProperties {

    private String secretKey;
    private Map<TokenType, Integer> intervalInMinutes;
}
