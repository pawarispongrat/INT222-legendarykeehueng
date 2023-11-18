package sit.int221.announcement.dtos.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import sit.int221.announcement.exceptions.validator.EnumValidator;
import sit.int221.announcement.exceptions.validator.IsEmail;
import sit.int221.announcement.exceptions.validator.UniqueKey;
import sit.int221.announcement.models.User;
import sit.int221.announcement.enumeration.Role;

@Getter
@Setter
@UniqueKey(model = User.class, fields = {"username","name","email"})
public class UserEdit {

    @NotBlank
    @Size(min = 1,max = 45)
    private String username;

    @NotBlank
    @Size(min = 1,max = 100)
    private String name;

    @IsEmail
    private String email;

    @EnumValidator(enumClass = Role.class,message = "must be either Role enum")
    private String role;

    public Role getRole() { return role == null ? Role.announcer : Role.valueOf(role.trim()); }
    public void setRole(String role) {
        this.role = role == null ? Role.announcer.toString() : role.trim();
    }

}
