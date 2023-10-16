package sit.int221.announcement.dtos.response;

import lombok.Getter;
import lombok.Setter;
import sit.int221.announcement.utils.enums.Role;

import java.time.ZonedDateTime;

@Getter @Setter
public class UserResponse {

    private Integer id;
    private String username;
    private String name;
    private String email;
    private Role role;

    private ZonedDateTime createdOn;
    private ZonedDateTime updatedOn;
}
