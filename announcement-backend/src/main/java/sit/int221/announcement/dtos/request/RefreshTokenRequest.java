package sit.int221.announcement.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RefreshTokenRequest {

    @NotBlank
    private String refreshToken;
}
