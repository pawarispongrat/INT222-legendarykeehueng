package sit.int221.announcement.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserLoginDTO {

    @NotBlank
    @Size(min = 1,max = 45)
    private String username;

    @NotBlank
    @Size(min = 8,max = 14)
    private String password;

    public void setPassword(String password) { this.password = password != null ? password.trim() : null; }

    public void setUsername(String username) { this.username = username != null ? username.trim() : null; }
}
