package sit.int221.announcement.dtos.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import sit.int221.announcement.exceptions.validator.EnumValidator;
import sit.int221.announcement.exceptions.validator.UniqueKey;
import sit.int221.announcement.models.User;
import sit.int221.announcement.utils.enums.Role;

@Getter @Setter
@UniqueKey(model = User.class, fields = {"username","name","email"})
public class UserRegisterDTO {

    @NotBlank
    @Size(min = 1,max = 45)
    private String username;

    @NotBlank
    @Size(min = 1,max = 100)
    private String name;

    @NotBlank
    @Size(min = 1,max = 150)
    @Email(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9._-]+",message = "Email should be valid")
    private String email;

    @EnumValidator(enumClass = Role.class,message = "must be either Role enum")
    private String role;

    @NotBlank
    @Size(min = 8,max = 14)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,14}$",message = "must be 8-14 characters long, at least 1 of uppercase, lowercase, number and special characters")
    private String password;
    public Role getRole() { return role == null ? Role.announcer : Role.valueOf(role.trim()); }

}
