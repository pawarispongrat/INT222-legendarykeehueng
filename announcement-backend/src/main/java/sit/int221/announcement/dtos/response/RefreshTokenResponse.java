package sit.int221.announcement.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RefreshTokenResponse {

    private String token;
    private String refreshToken;
}
