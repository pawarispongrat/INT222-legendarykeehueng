package sit.int221.announcement.dtos.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import sit.int221.announcement.exceptions.validator.EnumValidator;
import sit.int221.announcement.exceptions.validator.UniqueKey;
import sit.int221.announcement.models.User;
import sit.int221.announcement.utils.enums.Role;

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

    @NotBlank
    @Size(min = 1,max = 150)
    @Email(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9._-]+",message = "Email should be valid")
    private String email;

    @EnumValidator(enumClass = Role.class,message = "must be either Role enum")
    private String role;

//    public Role getRole() { return Role.valueOf(role.trim()); }

    public void setRole(String role) {
        this.role = role == null ? Role.announcer.toString() : role.trim();
    }

}
