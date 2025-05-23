package sit.int221.announcement.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties
public class UserLogin {

    @NotBlank
    @Size(min = 1,max = 45)
    private String username;

    @NotBlank
    @Size(min = 8,max = 14)
    private String password;

}
