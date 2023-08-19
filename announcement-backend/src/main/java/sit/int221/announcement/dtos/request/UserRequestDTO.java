package sit.int221.announcement.dtos.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import sit.int221.announcement.exceptions.validator.EnumValidator;
import sit.int221.announcement.utils.enums.Display;
import sit.int221.announcement.utils.enums.Role;

@Getter
@Setter
public class UserRequestDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String name;
    @NotBlank
    private String email;

    @EnumValidator(enumClass = Role.class,message = "must be either Role enum")
    private String role;

}
