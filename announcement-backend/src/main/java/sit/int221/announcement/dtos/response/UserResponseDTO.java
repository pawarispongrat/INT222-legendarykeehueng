package sit.int221.announcement.dtos.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import sit.int221.announcement.utils.enums.Role;

import java.time.ZonedDateTime;

@Getter @Setter
public class UserResponseDTO {

    private Integer id;
    private String username;
    private String name;
    private String email;
    private Role role;

    private ZonedDateTime createdOn;
    private ZonedDateTime updatedOn;
}
