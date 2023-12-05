package sit.int221.announcement.utils.security.entra;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "azure.entra")
@Getter
@Setter
public class EntraProperties {

    private String tenantId;
    private String clientSecret;
    private String clientId;
}
