package sit.int221.announcement.utils.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.mail")
@Getter @Setter
public class EmailProperties {

    private String host;

    private Integer port;

    private String username;

    private String password;

    private String transportProtocol;
    private Boolean smtpAuth;
    private Boolean smtpTlsEnable;
    private Boolean debug;

}

